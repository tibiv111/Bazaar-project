package project.bazaar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import project.bazaar.R
import project.bazaar.model.Order
import project.bazaar.model.OrderDetailData
import project.bazaar.model.Product
import project.bazaar.model.userData
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.DetailFragmentViewModel
import project.bazaar.viewmodels.DetailFragmentViewModelFactory
import project.bazaar.viewmodels.OrderDetailViewModel
import project.bazaar.viewmodels.OrderDetailViewModelFactory


class OrderDetailFragment : Fragment() {
    private lateinit var order: Order
    private lateinit var orderDetailChangeButton : Button
    private lateinit var title : TextInputEditText
    private lateinit var price : TextInputEditText
    private lateinit var status : Spinner
    private lateinit var orderDetailTotalPrice : TextInputEditText
    //private lateinit var amountType : TextInputEditText
    private lateinit var description : TextInputEditText
    private lateinit var orderDetailViewModel: OrderDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrderDetailViewModelFactory(this.requireContext(), Repository())
        orderDetailViewModel = ViewModelProvider(this, factory).get(OrderDetailViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = "Product detail"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        //actionBar?.setLogo(R.drawable.ic_bazaar_logo_coloured)
        actionBar?.setDisplayUseLogoEnabled(false)
        val view = inflater.inflate(R.layout.fragment_order_detail, container, false)
        order = OrderDetailData.getOrder()
        orderDetailChangeButton = view.findViewById(R.id.orderDetailChangeButton)
        title = view.findViewById(R.id.orderDetailTitle)
        price = view.findViewById(R.id.orderDetailPriceAmount)
        orderDetailTotalPrice = view.findViewById(R.id.orderDetailTotalPrice)
        status = view.findViewById(R.id.appCompatSpinner)
        var list_of_items = arrayOf("Incoming", "Accepted", "Declined", "Delivering", "Delivered")
        val aa = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, list_of_items)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        status.adapter = aa

        description = view.findViewById(R.id.orderDetailDescription)



        price.setText(order.price_per_unit)
        val fullPrice = (order.price_per_unit.toInt() * order.units.toInt()).toString()
        orderDetailTotalPrice.setText(fullPrice)
        orderDetailTotalPrice.isEnabled = false
        var index = 0
        for(item in list_of_items)
        {
            if(item == order.status)
            {
                break
            }
            index += 1
        }
        if(index>4)
        {
            index = 0
        }

        status.setSelection(index)

        description.setText(order.description)


        if(order.owner_username.removeSurrounding("\"") == userData.getUsername().removeSurrounding("\""))
        {
            orderDetailChangeButton.visibility = View.VISIBLE
            val str = order.title + " to " + order.username
            title.setText(str)

        }
        else
        {
            orderDetailChangeButton.visibility = View.GONE
            val str = order.title + " from " + order.owner_username
            title.setText(str)
            title.isEnabled = false
            price.isEnabled = false
            status.isEnabled = false
            description.isEnabled = false
            orderDetailTotalPrice.isEnabled = false
        }

        orderDetailChangeButton.setOnClickListener {
            orderDetailViewModel.order.value.let {
                if(it!= null)
                {
                    it.title =title.text.toString()
                    it.status = status.selectedItem.toString()
                    it.price_per_unit = price.text.toString()
                    it.description = description.text.toString()
                    it.order_id = order.order_id
                    it.owner_username = order.owner_username
                    it.username = order.username
                    it.messages = order.messages
                    it.creation_time = order.creation_time
                    it.images = order.images

                }
            }
            lifecycleScope.launch {
                val isSuccessful = orderDetailViewModel.updateOrder()
            }
        }

        return view
    }


}