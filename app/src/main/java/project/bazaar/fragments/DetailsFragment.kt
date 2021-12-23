package project.bazaar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import project.bazaar.R
import project.bazaar.model.Product
import project.bazaar.model.ProductDetailData
import project.bazaar.model.userData
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.DetailFragmentViewModel
import project.bazaar.viewmodels.DetailFragmentViewModelFactory
import project.bazaar.viewmodels.ProfileViewModel
import project.bazaar.viewmodels.ProfileViewModelFactory


class DetailsFragment : Fragment() {
    private lateinit var product: Product
    private lateinit var productDetailChangeButton : Button
    private lateinit var title : TextInputEditText
    private lateinit var seller : TextView
    private lateinit var is_active : TextView
    private lateinit var price : TextInputEditText
    private lateinit var priceType : TextInputEditText
    private lateinit var amountType : TextInputEditText
    private lateinit var description : TextInputEditText
    private lateinit var detailFragmentViewModel: DetailFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = DetailFragmentViewModelFactory(this.requireContext(), Repository())
        detailFragmentViewModel = ViewModelProvider(this, factory).get(DetailFragmentViewModel::class.java)
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
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        product = ProductDetailData.getProduct()
        title = view.findViewById(R.id.detailProductName)
        seller = view.findViewById(R.id.detailSeller)
        is_active = view.findViewById(R.id.detailActive)
        price = view.findViewById(R.id.detailPrice)
        priceType = view.findViewById(R.id.detailPriceType)
        amountType = view.findViewById(R.id.detailAmountType)
        description = view.findViewById(R.id.detailDescription)

        title.setText(product.title)
        seller.text = product.username
        price.setText(product.price_per_unit)
        var isActiveVariable : Boolean
        if(product.is_active)
        {
            is_active.text = "Active"
            isActiveVariable = true
            is_active.setTextColor(resources.getColor(R.color.turqoise))
        }
        else
        {
            isActiveVariable = false
            is_active.text = "Inactive"
            is_active.setTextColor(resources.getColor(R.color.dark_hintColorGray))
        }
        priceType.setText(product.price_type)
        amountType.setText(product.amount_type)
        description.setText(product.description)


        productDetailChangeButton = view.findViewById(R.id.productDetailChangeButton)
        if(userData.getUsername().removeSurrounding("\"") == product.username.removeSurrounding("\""))
        {
            productDetailChangeButton.visibility = View.VISIBLE


        }
        else
        {
            productDetailChangeButton.visibility = View.GONE
            seller.isEnabled = false
            title.isEnabled = false
            price.isEnabled = false
            priceType.isEnabled = false
            amountType.isEnabled = false
            description.isEnabled = false
        }

        actionBar?.show()
        productDetailChangeButton.setOnClickListener {

            detailFragmentViewModel.product.value.let {
                if(it != null)
                {
                    it.rating = product.rating
                    it.amount_type = amountType.text.toString()
                    it.price_type = priceType.text.toString()
                    it.product_id = product.product_id
                    it.username = product.username
                    it.is_active = isActiveVariable
                    it.price_per_unit = price.text.toString()
                    it.units = product.units
                    it.description = product.description
                    it.title = title.text.toString()
                }

            }

            lifecycleScope.launch {
                val isSuccessful = detailFragmentViewModel.updateProduct()
                if(isSuccessful)
                {

                }



            }
        }

        return view
    }


}