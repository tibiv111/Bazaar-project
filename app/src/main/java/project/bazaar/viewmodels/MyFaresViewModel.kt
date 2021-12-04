package project.bazaar.viewmodels



import android.util.Log
import androidx.lifecycle.*
import project.bazaar.Bazaar
import project.bazaar.model.LoginRequest
import project.bazaar.model.Product
import project.bazaar.model.User
import project.bazaar.repository.Repository
import project.bazaar.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.bazaar.model.userData

class MyFaresViewModel(private val repository: Repository) : ViewModel() {
    var products: MutableLiveData<List<Product>> = MutableLiveData()

    init{
        Log.d("xxx", "ListViewModel constructor - Token: ${Bazaar.token}")
        getProductsOfUser()
    }


    fun getProductsOfUser()
    {
        val username = userData.getUsername()
        val filter : String = "{\"username\": \"${username}\"}"
        viewModelScope.launch {
            try {
                val result =
                    repository.getProductsOfUser(Bazaar.token, filter = filter)
                products.value = result.products
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")
            }catch(e: Exception){
                Log.d("xxx", "ListViewModel exception: ${e.toString()}")
            }
        }
    }
}