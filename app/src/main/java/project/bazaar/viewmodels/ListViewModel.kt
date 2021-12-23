package project.bazaar.viewmodels



import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import project.bazaar.Bazaar
import project.bazaar.model.Product
import project.bazaar.repository.Repository
import kotlinx.coroutines.launch
import project.bazaar.model.userData

class ListViewModel(private val context: Context, private val repository: Repository) : ViewModel() {
    var products: MutableLiveData<List<Product>> = MutableLiveData()

    init{
        Log.d("xxx", "ListViewModel constructor - Token: ${Bazaar.token}")
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                val result =
                    repository.getProducts(Bazaar.token, 0)

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
        getProducts()
    }

    suspend fun addOrder(title: String, description : String, price_per_unit : String, units : String, owner_username : String) : String
    {
            var msg : String = "Order added successfully"

            try {
                val result = repository.addOrder(Bazaar.token, title, description, price_per_unit, units, owner_username.removeSurrounding("\""))
                Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
                Log.d("xxx3", result.toString())

            }catch (e: Exception) {
                Log.d("ccc", e.toString())
                if ( "300" in e.toString()) {
                    msg = "Token not sent in header"
                    Toast.makeText(this.context, "Username or password missing!", Toast.LENGTH_SHORT).show()
                    //Toast.makeText(this,"Token not sent in header", Toast.LENGTH_SHORT).show()
                }
                if ("301" in e.toString()) {
                    msg ="Invalid token"

                    Toast.makeText(this.context, "Invalid token", Toast.LENGTH_SHORT).show()
                }
                if ("302" in e.toString()) {
                    msg = "Token expired, please log in again"

                    Toast.makeText(this.context, "Token expired, please log in again", Toast.LENGTH_SHORT).show()
                }
                if ("303" in e.toString()) {
                    msg = "Title, description , price or quantity missing"

                    Toast.makeText(this.context, "Title, description , price or quantity missing", Toast.LENGTH_SHORT).show()
                }
                if ("304" in e.toString()) {
                    msg = "Error inserting in database"

                    Toast.makeText(this.context, "Product id header not sent.", Toast.LENGTH_SHORT).show()
                }
            }

            return msg
    }

    fun updateProduct(product_id : String, token: String, product: Product)
    {

    }

    fun getProductsOfUser()
    {
        val username = userData.getUsername()
        val filter : String = "{\"username\": \"${username}\"}"
        viewModelScope.launch {
            try {
                val result =
                    repository.getProductsOfUser(Bazaar.token, filter = filter, limit = 0)
                products.value = result.products
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")
            }catch(e: Exception){
                Log.d("xxx", "ListViewModel exception: ${e.toString()}")
            }
        }
    }
}