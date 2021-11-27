package project.bazaar.api


import project.bazaar.model.LoginRequest
import project.bazaar.model.LoginResponse
import project.bazaar.model.ProductResponse
import project.bazaar.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String): ProductResponse
}