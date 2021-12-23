package project.bazaar.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitAll
import project.bazaar.HomeActivity
import project.bazaar.R
import project.bazaar.adapters.DataAdapter
import project.bazaar.adapters.DataAdapterMyMarket
import project.bazaar.model.Product
import project.bazaar.model.ProductDetailData
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.ListViewModel
import project.bazaar.viewmodels.ListViewModelFactory
import project.bazaar.viewmodels.MyMarketViewModel
import project.bazaar.viewmodels.MyMarketViewModelFactory

class MyMarketFragment : Fragment(), DataAdapterMyMarket.OnItemClickListener, DataAdapterMyMarket.OnItemLongClickListener {
    lateinit var myMarketViewModel: MyMarketViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var adapter: DataAdapterMyMarket
    private lateinit var addButton: FloatingActionButton
    private lateinit var product_id : String
    private lateinit var popupMenu: PopupMenu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyMarketViewModelFactory(Repository())
        myMarketViewModel = ViewModelProvider(this, factory).get(MyMarketViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //here I set up the required actionbar for my fragment
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = "My Market"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        //actionBar?.setLogo(R.drawable.ic_bazaar_logo_coloured)
        actionBar?.setDisplayUseLogoEnabled(false)

        actionBar?.show()



        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_my_market, container, false)
        addButton = view.findViewById(R.id.addButtonMyMarket)

        recycler_view = view.findViewById(R.id.recycler_view3)
        setupRecyclerView()
        myMarketViewModel.products.observe(viewLifecycleOwner){
            adapter.setData(myMarketViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_myMarketFragment_to_addMarketItemFragment)
        }



        return view
    }

    private fun setupRecyclerView(){
        adapter = DataAdapterMyMarket(ArrayList<Product>(), this.requireContext(), this, this) //here we
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        recycler_view.setHasFixedSize(true)
        //registerForContextMenu(recycler_view)


    }

    override fun onItemClick(position: Int) {
        Log.d("xxx", "Item $position was clicked")
        val clickedItem = myMarketViewModel.products.value!![position]
        ProductDetailData.changeWholeProduct(clickedItem)
        findNavController().navigate(R.id.detailsFragment)

    }

    override fun onItemLongClick(position: Int) {

        val clickedItem = myMarketViewModel.products.value!![position]
        product_id = clickedItem.product_id
        //Toast.makeText(this.context, "long click pressed", Toast.LENGTH_SHORT).show()
        popupMenu = PopupMenu(requireActivity().applicationContext,recycler_view.getChildAt(position))
        popupMenu.menuInflater.inflate(R.menu.context_menu_for_delete,popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.deleteItemMenuId -> {
                    val msg = myMarketViewModel.deleteProduct(product_id = product_id)
                    Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
                    //2 WAYS OF REFRESH.
                    // 1. REFRESH FROM BACKEND

                    //myMarketViewModel.refresh()

                    // 2. IF SUCCESSFUL BACKEND THEN DELETE FROM ADAPTER


                    if(msg.compareTo("Item successfully deleted") == 0)
                    {
                        adapter.removeItem(clickedItem)
                        adapter.notifyDataSetChanged()
                    }



                }
            }
            true
        }
        popupMenu.show()
    }


    override fun onContextItemSelected(item: MenuItem ): Boolean {
        if(item.itemId == R.id.deleteItemMenuId )
        {
            myMarketViewModel.deleteProduct(product_id = product_id)
            adapter.notifyDataSetChanged()
            return true
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.context_menu_for_delete, menu)




    }


}

