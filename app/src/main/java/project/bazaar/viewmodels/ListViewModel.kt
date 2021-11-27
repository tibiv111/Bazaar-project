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

class ListViewModel(private val repository: Repository) : ViewModel() {
    var products: MutableLiveData<List<Product>> = MutableLiveData()

    init{
        Log.d("xxx", "ListViewModel constructor - Token: ${Bazaar.token}")
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                val result =
                    repository.getProducts(Bazaar.token)
                products.value = result.products
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")
            }catch(e: Exception){
                Log.d("xxx", "ListViewMofdel exception: ${e.toString()}")
            }
        }
    }
}