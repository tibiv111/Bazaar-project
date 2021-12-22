package project.bazaar.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import project.bazaar.R
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.MyMarketViewModel
import project.bazaar.viewmodels.MyMarketViewModelFactory


class AddMarketItemFragment : Fragment() {

    lateinit var myMarketViewModel: MyMarketViewModel
    private lateinit var activeButton : Button
    private lateinit var addItemButton: Button
    private lateinit var amount : TextInputEditText
    private lateinit var price : TextInputEditText
    private lateinit var title : TextInputEditText
    private lateinit var priceType : TextInputEditText
    private lateinit var amountType : TextInputEditText
    private lateinit var rating : TextInputEditText
    private lateinit var description : TextInputEditText


    /*


     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyMarketViewModelFactory(Repository())
        myMarketViewModel = ViewModelProvider(this, factory).get(MyMarketViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_market_item, container, false)
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = "Create your fare"
        actionBar?.setDisplayShowHomeEnabled(false)
        //actionBar?.setDisplayHomeAsUpEnabled(false)
        //actionBar?.setLogo(R.drawable.ic_bazaar_logo_coloured)
        actionBar?.setDisplayUseLogoEnabled(false)

        actionBar?.show()

        activeButton = view.findViewById(R.id.isActiveButton)
        addItemButton = view.findViewById(R.id.ADD_add_item_button)
        amount = view.findViewById(R.id.ADD_amount)
        price = view.findViewById(R.id.ADD_price_per_amount)
        title = view.findViewById(R.id.addTitleTextView)
        priceType = view.findViewById(R.id.ADD_price_type)
        amountType = view.findViewById(R.id.ADD_amount_type)
        rating = view.findViewById(R.id.ADD_rating)
        description = view.findViewById(R.id.ADD_description)

        activeButton.setOnClickListener{
            if(activeButton.text == "ACTIVE")
            {
                activeButton.text = "INACTIVE"
                ViewCompat.setBackgroundTintList(activeButton, ColorStateList.valueOf(resources.getColor(R.color.hintColorGray)));
                //activeButton.backgroundTint(resources.getColor(R.color.dark_hintColorGray))
                //activeButton.setTextColor(Color.BLACK)
            }
            else
            {
                activeButton.text = "ACTIVE"
                ViewCompat.setBackgroundTintList(activeButton, ColorStateList.valueOf(resources.getColor(R.color.turqoise)));
                //activeButton.setBackgroundColor(resources.getColor(R.color.turqoise))

                //activeButton.setTextColor(Color.BLACK)
            }
        }

        addItemButton.setOnClickListener {
            if(amount.text.isNullOrBlank() || price.text.isNullOrBlank() || title.text.isNullOrBlank() ||
                    priceType.text.isNullOrBlank() || amountType.text.isNullOrBlank() || description.text.isNullOrBlank() || rating.text.isNullOrBlank())
            {
                Toast.makeText(this.context, "Please fill all the empty cells", Toast.LENGTH_SHORT).show()
            }
            else
            {
                var isActive : Boolean = false
                isActive = activeButton.text == "ACTIVE"
                val msg = myMarketViewModel.addproduct(title = title.text.toString(),
                    description = description.text.toString(), price_per_unit = price.text.toString(),
                    units = amount.text.toString(), is_active = isActive,
                    rating = rating.text.toString().toDouble(), amount_type = amountType.text.toString(),
                    price_type = priceType.text.toString())
                Log.d("vvv", msg)
                //Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
                myMarketViewModel.refresh()
                findNavController().navigate(R.id.action_addMarketItemFragment_to_myMarketFragment)
                //activity?.fragmentManager?.popBackStack()
            }
        }

        return view
    }




}