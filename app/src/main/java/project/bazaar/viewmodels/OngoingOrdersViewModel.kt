package project.bazaar.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.bazaar.Bazaar
import project.bazaar.model.Order
import project.bazaar.model.userData
import project.bazaar.repository.Repository

class OngoingOrdersViewModel(private val repository: Repository) : ViewModel() {
    var orders: MutableLiveData<List<Order>> = MutableLiveData()

    init {
        Log.d("xxx", "ListViewModel constructor - Token: ${Bazaar.token}")
        getOrdersOfUser()
    }


    private fun getOrdersOfUser() {
        val username = userData.getUsername()
        val filter: String = "{\"username\": \"${username}\"}"
        viewModelScope.launch {
            try {
                val result =
                    repository.getOrdersOfUser(Bazaar.token, filter = filter, limit = 0)
                for (order in result.orders) {

                    order.title = order.title.removeSurrounding("\"")
                    order.owner_username = order.owner_username.removeSurrounding("\"")
                    order.username = order.username.removeSurrounding("\"")
                    order.price_per_unit = order.price_per_unit.removeSurrounding("\"")
                    order.units = order.units.removeSurrounding("\"")
                    order.description = order.description.removeSurrounding("\"")

                }
                orders.value = result.orders
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")
            } catch (e: Exception) {
                Log.d("xxx", "ListViewModel exception: ${e.toString()}")
            }
        }
    }
}