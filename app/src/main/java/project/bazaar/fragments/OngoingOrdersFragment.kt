package project.bazaar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import project.bazaar.R
import project.bazaar.adapters.DataAdapterSales
import project.bazaar.model.Order
import project.bazaar.model.OrderDetailData
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.OngoingOrdersViewModel
import project.bazaar.viewmodels.OngoingOrdersViewModelFactory



class OngoingOrdersFragment : Fragment(), DataAdapterSales.OnItemClickListener, DataAdapterSales.OnItemLongClickListener {
    private lateinit var ongoingOrdersViewModel: OngoingOrdersViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var adapter: DataAdapterSales
    private lateinit var viewPager2: ViewPager2
    private lateinit var showMoreButton : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OngoingOrdersViewModelFactory(Repository())
        ongoingOrdersViewModel = ViewModelProvider(this, factory).get(OngoingOrdersViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //here I set up the required actionbar for my fragment
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = "My Fares" // ez a title
        actionBar?.setDisplayShowHomeEnabled(true) // ez nem tudom már
        actionBar?.setDisplayHomeAsUpEnabled(true) //Ez a vissza jel
        //actionBar?.setLogo(R.drawable.ic_bazaar_logo_coloured)
        actionBar?.setDisplayUseLogoEnabled(false) // Ez a logo a title mellett

        actionBar?.show()
        val view = inflater.inflate(R.layout.fragment_ongoing_orders, container, false)
        //viewPager2 = findViewById()
        recycler_view = view.findViewById(R.id.recycler_view23)
        setupRecyclerView()

        ongoingOrdersViewModel.orders.observe(viewLifecycleOwner){
            adapter.setData(ongoingOrdersViewModel.orders.value as ArrayList<Order>)
            adapter.notifyDataSetChanged()
        }
        return view
    }

    private fun setupRecyclerView(){
        adapter = DataAdapterSales(ArrayList<Order>(), this.requireContext(), this, this) //here we
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        recycler_view.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
        Log.d("xxx", "Item $position was clicked")
        val clickedItem = ongoingOrdersViewModel.orders.value!![position]
        OrderDetailData.changeWholeProduct(clickedItem)
        findNavController().navigate(R.id.orderDetailFragment)


    }

    override fun onItemLongClick(position: Int) {
    }


}
