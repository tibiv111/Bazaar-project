package project.bazaar.repository



import android.util.Log
import project.bazaar.Bazaar
import project.bazaar.api.RetrofitInstance
import project.bazaar.model.*
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Query

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getProducts(token: String, limit : Long): ProductResponse {
        return RetrofitInstance.api.getProducts(token, limit, "{\"creation_time\" : -1}")
    }

    suspend fun getProductsOfUser(token: String, filter: String, limit : Long) : ProductResponse
    {
        return RetrofitInstance.api.getProductsOfUser(token, filter, limit, "{\"creation_time\" : -1}")
    }
    suspend fun getOrdersOfUser(token: String, filter: String, limit : Long) : OrderResponse
    {
        return RetrofitInstance.api.getOrdersOfUser(token, filter, limit, "{\"creation_time\" : -1}")
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

    suspend fun deleteProduct(@Query("product_id") product_id : String, @Header("token") token: String) : DeleteProductResponse
    {
        return RetrofitInstance.api.deleteProduct(product_id, token)
    }

    suspend fun addProduct(@Header("token") token: String,
                           @Part("title") title : String,
                           @Part("description") description : String,
                           @Part("price_per_unit") price_per_unit: String,
                           @Part("units") units : String,
                           @Part("is_active") is_active: Boolean,
                           @Part("rating") rating: Double,
                           @Part("amount_type") amount_type: String,
                           @Part("price_type") price_type: String ) : AddProductResponse {
        return RetrofitInstance.api.addProduct(token, title, description, price_per_unit, units, is_active, rating, amount_type, price_type)
    }
    suspend fun addOrder(@Header("token") token: String,
                         @Part("title") title : String,
                         @Part("description") description : String,
                         @Part("price_per_unit") price_per_unit: String,
                         @Part("units") units : String,
                         @Part("owner_username") owner_username: String) : AddOrderResponse
    {
        return RetrofitInstance.api.addOrder(token, title, description, price_per_unit, units, owner_username)
    }




}