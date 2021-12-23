package project.bazaar.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.fragment.findNavController
import project.bazaar.Bazaar
import project.bazaar.R
import project.bazaar.model.*
import project.bazaar.repository.Repository


class OrderDetailViewModel(val context: Context, private val repository: Repository) : ViewModel() {


    var order = MutableLiveData<Order>()

    init {
        order.value = Order()
    }


    suspend fun updateOrder() : Boolean
    {
        var isSuccessful: Boolean = false
        val request =
            UpdateOrderRequest(
                title = order.value!!.title,
                status = order.value!!.status,
                price_per_unit = order.value!!.price_per_unit,
                amount_ordered = order.value!!.units,
                description = order.value!!.description
            )

        Log.d("rrr", request.toString())
        try {
            val result = repository.updateOrder(order.value!!.order_id, Bazaar.token, request)
            //FAULTY BACKEND: NO RESPONSE MESSAGE
            //changeDataAndImage(result.username, result.email, result.phone_number, user.value!!.userImage)
            val storedOrder = OrderDetailData.getOrder()

            OrderDetailData.changeOrder(
                order_id = storedOrder.order_id,
                username = storedOrder.username,
                status = request.status,
                owner_username = storedOrder.owner_username,
                price_per_unit = request.price_per_unit,
                units = storedOrder.units,
                description = request.description,
                title = request.title,
                images = storedOrder.images,
                creation_time = storedOrder.creation_time,
                messages = storedOrder.messages)
            isSuccessful = true
            Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

        }


        return isSuccessful
    }

}
