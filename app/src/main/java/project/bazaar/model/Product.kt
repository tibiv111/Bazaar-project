package project.bazaar.model

import com.squareup.moshi.JsonClass

import retrofit2.http.Multipart

/*
Data in json format for the REST api
 */

@JsonClass(generateAdapter = true)
data class Image(val _id: String, val image_id: String, val image_name: String, val image_path: String)

@JsonClass(generateAdapter = true)
data class Product(val rating: Double,
                   var amount_type: String,
                   var price_type: String,
                   val product_id: String,
                   val username: String,
                   val is_active: Boolean,
                   var price_per_unit: String,
                   var units: String,
                   var description: String,
                   var title: String,
                   var images: List<Image>,
                   val creation_time: Long
)

@JsonClass(generateAdapter = true)
data class ProductResponse(val item_count: Int, val products: MutableList<Product>, val timestamp: Long)






@JsonClass(generateAdapter = true)
data class DeleteProductResponse(
    var message: String,
    var product_id: String,
    var deletion_time: Long
)



@JsonClass(generateAdapter = true)
data class AddProductResponse(
    var creation: String,
    var product_id: String,
    var username: String,
    var is_active: Boolean,
    var price_per_unit: String,
    var units: String,
    var description: String,
    var title: String,
    var rating: String,
    var amount_type: String,
    var price_type: String,
    var images: List<*>,
    var creation_time: Long
)





