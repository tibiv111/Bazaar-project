package project.bazaar.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.bazaar.Bazaar
import project.bazaar.model.*
import project.bazaar.repository.Repository

class DetailFragmentViewModel(val context: Context, private val repository: Repository) : ViewModel() {


    var product = MutableLiveData<Product>()

    init {
        product.value = Product()
    }


    suspend fun updateProduct() : Boolean
    {
        var isSuccessful: Boolean = false
        val request =
            UpdateProductRequest(
                price_per_unit = product.value!!.price_per_unit.toLong(),
                is_active = product.value!!.is_active,
                title = product.value!!.title,
                description = product.value!!.description,
                units = product.value!!.units.toLong(),
                amount_type = product.value!!.amount_type,
                price_type = product.value!!.price_type
            )

        Log.d("rrr", request.toString())
        try {
            val result = repository.updateProduct(product.value!!.product_id, Bazaar.token, request)
            //FAULTY BACKEND: NO RESPONSE MESSAGE
            //changeDataAndImage(result.username, result.email, result.phone_number, user.value!!.userImage)
            val storedProduct = ProductDetailData.getProduct()

            ProductDetailData.changeProduct(
                product_id = storedProduct.product_id,
                creation_time = storedProduct.creation_time,
                images = storedProduct.images,
                username = storedProduct.username,
                price_per_unit = request.price_per_unit.toString(),
                is_active = request.is_active,
                title = request.title,
                rating = storedProduct.rating.toLong(),
                amount_type = request.amount_type,
                price_type = request.price_type,
                units = request.units.toString(),
                description = request.description
            )
            isSuccessful = true
            Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

        }

        return isSuccessful
    }

}


