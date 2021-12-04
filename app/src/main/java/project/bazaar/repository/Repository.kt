package project.bazaar.repository



import android.util.Log
import project.bazaar.Bazaar
import project.bazaar.api.RetrofitInstance
import project.bazaar.model.*

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }

    suspend fun getProductsOfUser(token: String, filter: String) : ProductResponse
    {
        return RetrofitInstance.api.getProductsOfUser(token, filter)
    }

    suspend fun register(request: RegisterRequest) : RegisterResponse{

        return RetrofitInstance.api.register(request)
    }

    suspend fun resetPassword(request: ResetPasswordRequest): GeneralResponse{
        return  RetrofitInstance.api.resetPassword(request)
    }


    suspend fun updateUserData(request: UpdateUserDataRequest): UpdateUserDataResponse
    {

        return  RetrofitInstance.api.updateUserData(Bazaar.token, request)
    }
}