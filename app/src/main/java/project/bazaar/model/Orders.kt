package project.bazaar.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddOrderResponse(
    val creation: String,
    var order_id: String,
    val username: String,
    val status: String,
    val owner_username: String,
    var price_per_unit: String,
    var units: String,
    var description: String,
    var title: String,
    var images: List<*>,
    val creation_time: Long

)

@JsonClass(generateAdapter = true)
data class Order(
    var order_id: String,
    var username: String,
    var status: String,
    var owner_username: String,
    var price_per_unit: String,
    var units: String,
    var description: String,
    var title: String,
    val images: List<*>,
    val creation_time: Long,
    val messages: List<*>
)

@JsonClass(generateAdapter = true)
data class OrderResponse(val item_count: Int, val orders: MutableList<Order>, val timestamp: Long)