package project.bazaar.viewmodels


import android.util.Log
import androidx.lifecycle.*
import project.bazaar.Bazaar
import project.bazaar.repository.Repository
import kotlinx.coroutines.launch
import project.bazaar.model.*


class MyMarketViewModel(private val repository: Repository) : ViewModel() {
    var products: MutableLiveData<List<Product>> = MutableLiveData()

    init{
        Log.d("xxx", "ListViewModel constructor - Token: ${Bazaar.token}")
        getProductsOfUser()
    }


    private fun getProductsOfUser()
    {
        val username = userData.getUsername()
        val filter : String = "{\"username\": \"${username}\"}"
        viewModelScope.launch {
            try {
                val result =
                    repository.getProductsOfUser(Bazaar.token, filter = filter, limit = 0)
                for (product in result.products)
                {
                    product.title = product.title.removeSurrounding("\"")
                    product.price_per_unit = product.price_per_unit.removeSurrounding("\"")
                    product.units = product.units.removeSurrounding("\"")
                    product.description = product.description.removeSurrounding("\"")
                    product.amount_type = product.amount_type.removeSurrounding("\"")
                    product.price_type = product.price_type.removeSurrounding("\"")
                }
                products.value = result.products
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")
            }catch(e: Exception){
                Log.d("xxx", "ListViewModel exception: ${e.toString()}")
            }
        }
    }

    fun refresh()
    {
        getProductsOfUser()
    }

    fun addproduct(title: String, description: String, price_per_unit: String, units : String,
                   is_active: Boolean, rating: Double, amount_type : String, price_type : String ) : String
    {
        var msg : String = "Item successfully added"

        viewModelScope.launch {
            try {
                val result = repository.addProduct(Bazaar.token,title=title, description=description,
                    price_per_unit=price_per_unit, units = units, is_active = is_active, rating = rating,
                    amount_type = amount_type, price_type = price_type )



                Log.d("ccc", result.toString())

            }catch (e: Exception)
            {
                Log.d("ccc", e.toString())
                if ( "300" in e.toString())
                {
                    msg = "Token not sent in header"

                    //Toast.makeText(this,"Token not sent in header", Toast.LENGTH_SHORT).show()
                }
                if ("301" in e.toString())
                {
                    msg ="Invalid token"

                    //Toast.makeText(this.context, "Invalid token", Toast.LENGTH_SHORT).show()
                }
                if ("302" in e.toString())
                {
                    msg = "Token expired, please log in again"

                    //Toast.makeText(this.context, "Token expired, please log in again", Toast.LENGTH_SHORT).show()
                }
                if ("303" in e.toString())
                {
                    msg = "Title, description , price or quantity missing"

                    //Toast.makeText(this.context, "Title, description , price or quantity missing", Toast.LENGTH_SHORT).show()
                }
                if ("304" in e.toString())
                {
                    msg = "Error inserting in database"

                    //Toast.makeText(this.context, "Product id header not sent.", Toast.LENGTH_SHORT).show()
                }

            }
        }
        return msg


    }
    fun deleteProduct( product_id : String) : String
    {
        var msg : String = "Item successfully deleted"

        viewModelScope.launch {

            try {
                val result =
                    repository.deleteProduct(product_id, Bazaar.token)



            }catch(e: Exception){
                //Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
                if ( "300" in e.toString())
                {
                    msg = "Token not sent in header"
                    //Toast.makeText(this,"Token not sent in header", Toast.LENGTH_SHORT).show()
                }
                if ("301" in e.toString())
                {
                    msg ="Invalid token"
                        //Toast.makeText(this.context, "Invalid token", Toast.LENGTH_SHORT).show()
                }
                if ("302" in e.toString())
                {
                    msg = "Token expired, please log in again"
                    //Toast.makeText(this.context, "Token expired, please log in again", Toast.LENGTH_SHORT).show()
                }
                if ("303" in e.toString())
                {
                    msg = "Title, description , price or quantity missing"
                    //Toast.makeText(this.context, "Title, description , price or quantity missing", Toast.LENGTH_SHORT).show()
                }
                if ("304" in e.toString())
                {
                    msg = "Product id header not sent."
                    //Toast.makeText(this.context, "Product id header not sent.", Toast.LENGTH_SHORT).show()
                }
                if ("305" in e.toString())
                {
                    msg = "Product not in database"
                    //Toast.makeText(this.context, "Product not in database", Toast.LENGTH_SHORT).show()
                }


            }

        }
        return msg
    }


}