package project.bazaar.api


import project.bazaar.model.*
import project.bazaar.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String): ProductResponse

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse

    @POST(Constants.RESET_PASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest) : GeneralResponse
}