package project.bazaar.repository



import android.util.Log
import project.bazaar.api.RetrofitInstance
import project.bazaar.model.LoginRequest
import project.bazaar.model.LoginResponse
import project.bazaar.model.ProductResponse

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }
}