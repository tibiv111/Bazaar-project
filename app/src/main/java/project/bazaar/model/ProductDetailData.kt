package project.bazaar.model

import kotlin.properties.Delegates

object ProductDetailData {

    private lateinit var product : Product


    fun getProduct() : Product
    {
        return this.product
    }

    fun changeWholeProduct(product: Product)
    {
        this.product = product
    }

    fun changeProduct (rating: Long, amount_type: String, price_type: String, product_id: String,
                      username: String, is_active: Boolean, price_per_unit: String,
                      units: String, description: String, title: String,images: List<Image>,
                      creation_time: Long)
    {
        this.product.product_id = product_id
        this.product.amount_type = amount_type
        this.product.creation_time = creation_time
        this.product.description = description
        this.product.rating = rating.toDouble()
        this.product.price_type = price_type
        this.product.username = username
        this.product.price_per_unit = price_per_unit
        this.product.units = units
        this.product.title = title
        this.product.images = images
        this.product.is_active = is_active

    }


}

