package project.bazaar.adapters

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import project.bazaar.R
import project.bazaar.model.Order
import project.bazaar.model.Product
import project.bazaar.model.userData

class DataAdapterSales(
    private var list: MutableList<Order>,
    private val context: Context,
    private val listener: DataAdapterSales.OnItemClickListener, //this is reusable(interface)!
    private val listener2: DataAdapterSales.OnItemLongClickListener
) : RecyclerView.Adapter<DataAdapterSales.DataViewHolder>(), Filterable {
    val listFull: MutableList<Order> = mutableListOf()


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {


        val textView_price_and_priceType: TextView = itemView.findViewById(R.id.textView_price_item_layout5)
        val textView_amount_and_amountType : TextView = itemView.findViewById(R.id.textView_item_price_amount_type3)
        val textView_name: TextView = itemView.findViewById(R.id.textView_name_item_layout3)
        val textView_seller: TextView = itemView.findViewById(R.id.textView_seller_item_layout3)
        val imageView: ImageView = itemView.findViewById(R.id.imageView_item_layout3)
        val seller_imageview : ImageView = itemView.findViewById(R.id.imageView_item_layout4)
        val showMoreButton : ImageButton = itemView.findViewById(R.id.showMoreButton)
        val description: TextView = itemView.findViewById(R.id.description_textview)

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
        //here the position could be changed to the whole item, but only if you have your data set
        // only available in the adapter

    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterSales.DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_ongoing_sales, parent, false)
        return DataViewHolder(itemView)
    }


    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataAdapterSales.DataViewHolder, position: Int) {
        val currentItem = list[position]
        if(currentItem.owner_username.removeSurrounding("\"") == userData.getUsername().removeSurrounding("\""))
        {
            val title = currentItem.title + " to " + currentItem.username.removeSurrounding("\"")
            holder.textView_name.text = title

        }
        else
        {
            val title = currentItem.title + " from " + currentItem.owner_username.removeSurrounding("\"")
            holder.textView_name.text = title
        }


        val space = currentItem.price_per_unit
        holder.textView_price_and_priceType.text = space
        val space2 = currentItem.units
        holder.textView_amount_and_amountType.text = space2
        val seller = currentItem.owner_username
        holder.textView_seller.text = seller
        holder.description.text = currentItem.description

        val images = currentItem.images
        if( images != null && images.isNotEmpty()) {
            Log.d("xxx", "#num_images: ${images.size}")
            Glide.with(this.context)
                .load(images[0])
                .override(200, 200)
                .into(holder.imageView);
            Glide.with(this.context)
                .load(R.drawable.ic_tile_1)
                //.load(images[0])
                .override(80, 80)
                .into(holder.seller_imageview);
        }else
        {
            Glide.with(this.context)
                .load(R.drawable.ic_tile_1)
                //.load(images[0])
                .override(200, 200)
                .into(holder.imageView);
            Glide.with(this.context)
                .load(R.drawable.ic_tile_1)
                //.load(images[0])
                .override(80, 80)
                .into(holder.seller_imageview);
        }


        holder.showMoreButton.setOnClickListener {

            if(holder.description.visibility == View.GONE)
            {
                holder.description.visibility = View.VISIBLE
                holder.showMoreButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }
            else
            {
                holder.description.visibility = View.GONE
                holder.showMoreButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }


        }

    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: MutableList<Order>){
        list = newlist
    }

    override fun getFilter(): Filter {
        return searchFilter
    }
    private val searchFilter = object : Filter()
    {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Order>()
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

            list.addAll(p1?.values as MutableList<Order>)
            notifyDataSetChanged()


        }

    }


}