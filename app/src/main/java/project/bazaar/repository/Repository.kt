package project.bazaar.repository



import android.util.Log
import project.bazaar.api.RetrofitInstance
import project.bazaar.model.*

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }

    suspend fun register(request: RegisterRequest) : RegisterResponse{

        return RetrofitInstance.api.register(request)
    }
}