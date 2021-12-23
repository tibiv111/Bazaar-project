package project.bazaar.viewmodels



import android.util.Log
import androidx.lifecycle.*
import project.bazaar.Bazaar
import project.bazaar.repository.Repository
import project.bazaar.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.bazaar.model.*

class OngoingSalesViewModel(private val repository: Repository) : ViewModel() {
    var orders: MutableLiveData<List<Order>> = MutableLiveData()

    init{
        Log.d("xxx", "ListViewModel constructor - Token: ${Bazaar.token}")
        getOrdersOfUser()
    }


    private fun getOrdersOfUser()
    {
        val username = userData.getUsername().removeSurrounding("\"")
        val filter : String = "{\"owner_username\": \"\\\"${username}\\\"\"}"

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
    /*
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
                orders.value = result.
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")
            }catch(e: Exception){
                Log.d("xxx", "ListViewModel exception: ${e.toString()}")
            }
        }
    }

     */
}