package project.bazaar.model

object OrderDetailData {

    private lateinit var order : Order


    fun getOrder() : Order
    {
        return this.order
    }

    fun changeWholeProduct(order: Order)
    {
        this.order = order
    }

    fun changeOrder (order_id : String, username : String, status : String, owner_username : String,
                    price_per_unit : String, units : String, description : String, title : String,
                    images: List<Image>, creation_time : Long, messages : List<String>)
    {
        this.order.order_id = order_id
        this.order.username = username
        this.order.status = status
        this.order.owner_username = owner_username
        this.order.price_per_unit = price_per_unit
        this.order.units = units
        this.order.description = description
        this.order.title = title
        this.order.images = images
        this.order.creation_time = creation_time
        this.order.messages = messages

    }
}