package project.bazaar.api


import project.bazaar.model.*
import project.bazaar.utils.Constants
import retrofit2.Response
import project.bazaar.Bazaar
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse
//{\"creation_time\" : 1}
    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String, @Header("limit") limit : Long, @Header("sort") sort : String): ProductResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProductsOfUser(@Header("token") token: String, @Header("filter") filter : String, @Header("limit") limit : Long, @Header("sort") sort : String): ProductResponse



    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse

    @POST(Constants.RESET_PASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest) : GeneralResponse

    @POST(Constants.UPDATE_USER_DATA_URL)
    suspend fun updateUserData(@Header("token") token: String, @Body request: UpdateUserDataRequest) : UpdateUserDataResponse

    @POST(Constants.DELETE_PRODUCT_URL)
    suspend fun deleteProduct(@Query("product_id") product_id : String, @Header("token") token: String) : DeleteProductResponse

    @POST(Constants.UPDATE_PRODUCT)
    suspend fun updateProduct(@Query("product_id") product_id: String, @Header("token") token: String, @Body request : UpdateProductRequest) : Product


    @Multipart
    @POST(Constants.ADD_PRODUCT)
    suspend fun addProduct(@Header("token") token: String,
                            @Part("title") title : String,
                            @Part("description") description : String,
                           @Part("price_per_unit") price_per_unit: String,
                           @Part("units") units : String,
                           @Part("is_active") is_active: Boolean,
                           @Part("rating") rating: Double,
                           @Part("amount_type") amount_type: String,
                           @Part("price_type") price_type: String
                                ) : AddProductResponse



    @Multipart
    @POST(Constants.ADD_ORDER)
    suspend fun addOrder(@Header("token") token: String,
                        @Part("title") title : String,
                        @Part("description") description : String,
                        @Part("price_per_unit") price_per_unit: String,
                        @Part("units") units : String,
                        @Part("owner_username") owner_username: String) : AddOrderResponse


    @GET(Constants.GET_ORDERS)
    suspend fun getOrdersOfUser(@Header("token") token: String, @Header("filter") filter : String, @Header("limit") limit : Long, @Header("sort") sort : String): OrderResponse

    @POST(Constants.UPDATE_ORDER)
    suspend fun updateOrder(@Query("order_id") order_id: String, @Header("token") token: String, @Body request : UpdateOrderRequest) : Order


}