package project.bazaar.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import project.bazaar.model.Product
import project.bazaar.R
import project.bazaar.model.userData

/*
Adapter + Viewholder:

The adapter's role is to convert an object at a position into a list row item to be inserted.
However, with a RecyclerView the adapter requires the existence of a "ViewHolder" object which
describes and provides access to all the views within each item row.
 */

class DataAdapter(
    private var list: MutableList<Product>,
    private val context: Context,
    private val listener: OnItemClickListener, //this is reusable(interface)!
    private val listener2: OnItemLongClickListener
) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>(), Filterable {
    val listFull: MutableList<Product> = mutableListOf()


    // 1. user defined ViewHolder type - Embedded class!
    //It's inner class because of the  "listener.onItemClick(currentPosition)"
    //more explicitly we cannot use the recycleview without the dataviewholder class because it's inner
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val textView_name: TextView = itemView.findViewById(R.id.textView_name_item_layout)
        val textView_price: TextView = itemView.findViewById(R.id.textView_price_item_layout)
        val textView_amountType : TextView = itemView.findViewById(R.id.textView_item_price_amount_type)
        val textView_priceType : TextView = itemView.findViewById(R.id.textView_item_price_type)
        val textView_seller: TextView = itemView.findViewById(R.id.textView_seller_item_layout)
        val imageView: ImageView = itemView.findViewById(R.id.imageView_item_layout)
        val addOrderButton : Button = itemView.findViewById(R.id.addOrderButton)


            init{
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            if(listFull.isEmpty())
            {
                listFull.addAll(list)
            }

            //Log.d("xxx2", listFull.toString())


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
        fun onAddOrderButtonClick(position: Int)
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
        val space = currentItem.price_per_unit + " "
        holder.textView_price.text = space
        val per = currentItem.price_type + "/"
        holder.textView_priceType.text = per
        holder.textView_amountType.text = currentItem.amount_type
        val seller = "Seller: " + currentItem.username
        holder.textView_seller.text = seller
        if(currentItem.username.removeSurrounding("\"") == userData.getUsername().removeSurrounding("\""))
        {
            holder.addOrderButton.isEnabled = false
        }


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
        holder.addOrderButton.setOnClickListener {
            val position : Int = holder.adapterPosition
            if(position != RecyclerView.NO_POSITION)
            {
                holder.addOrderButton.isEnabled = false
                listener.onAddOrderButtonClick(position)

            }
        }




    }




    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: MutableList<Product>){
        list = newlist
    }

    override fun getFilter(): Filter {
        return searchFilter
    }
    private val searchFilter = object : Filter()
    {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Product>()
            if(p0 == null || p0.isEmpty())
            {
                filteredList.addAll(listFull)
            }
            else
            {
                for(item in listFull)
                {
                    if(item.title.toLowerCase().trim().contains(p0.toString().toLowerCase().trim()))
                    {
                        filteredList.add(item);
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results

        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            list.clear()
            //listFull.clear()

            list.addAll(p1?.values as MutableList<Product>)
            notifyDataSetChanged()


        }

    }


}

