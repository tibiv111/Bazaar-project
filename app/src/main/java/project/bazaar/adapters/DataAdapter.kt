package project.bazaar.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import project.bazaar.model.Product
import project.bazaar.R

/*
Adapter + Viewholder:

The adapter's role is to convert an object at a position into a list row item to be inserted.
However, with a RecyclerView the adapter requires the existence of a "ViewHolder" object which
describes and provides access to all the views within each item row.
 */
class DataAdapter(
    private var list: ArrayList<Product>,
    private val context: Context,
    private val listener: OnItemClickListener, //this is reusable(interface)!
    private val listener2: OnItemLongClickListener
) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {



    // 1. user defined ViewHolder type - Embedded class!
    //It's inner class because of the  "listener.onItemClick(currentPosition)"
    //more explicitly we cannot use the recycleview without the dataviewholder class because it's inner
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val textView_name: TextView = itemView.findViewById(R.id.textView_name_item_layout)
        val textView_price: TextView = itemView.findViewById(R.id.textView_price_item_layout)
        val textView_seller: TextView = itemView.findViewById(R.id.textView_seller_item_layout)
        val imageView: ImageView = itemView.findViewById(R.id.imageView_item_layout)

        init{
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)

        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            if(currentPosition != RecyclerView.NO_POSITION) //NO_POSITION = -1
            {
                    //it's possible that we delete an item but click it before it's completely
                    // animated off from recyleview, since recycleview has built-in animation
                listener2.onItemLongClick(currentPosition)
            }

            return true
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
        //here the position could be changed to the whole item, but only if you have your data set
        // only available in the adapter

    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(itemView)
    }


    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textView_name.text = currentItem.title
        holder.textView_price.text = currentItem.price_per_unit
        holder.textView_seller.text = currentItem.username

        val images = currentItem.images
        if( images != null && images.isNotEmpty()) {
            Log.d("xxx", "#num_images: ${images.size}")
            Glide.with(this.context)
                .load(images[0])
                .override(200, 200)
                .into(holder.imageView);
        }else
        {
            Glide.with(this.context)
                .load(R.drawable.ic_tile_1)
                //.load(images[0])
                .override(300, 300)
                .into(holder.imageView);
        }

    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: ArrayList<Product>){
        list = newlist
    }
}

