package project.bazaar.fragments



import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.bazaar.R
import project.bazaar.adapters.DataAdapter
import project.bazaar.model.Product
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.ListViewModel
import project.bazaar.viewmodels.ListViewModelFactory
import androidx.recyclerview.widget.DividerItemDecoration

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.Filterable
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import project.bazaar.model.userData
import project.bazaar.model.ProductDetailData
import kotlin.properties.Delegates


/*
List fragment is created for the recycleview to populate
 */
class ListFragment : Fragment() , DataAdapter.OnItemClickListener, DataAdapter.OnItemLongClickListener {
    lateinit var listViewModel: ListViewModel
    private var  firstResume by Delegates.notNull<Boolean>()
    private lateinit var recycler_view: RecyclerView
    private lateinit var adapter: DataAdapter

    private lateinit var searchView : androidx.appcompat.widget.SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory(this.requireContext(), Repository())
        listViewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)
        firstResume = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //setHasOptionsMenu(true);
        //here I set up the required actionbar for my fragment
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = ""
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setLogo(R.drawable.ic_bazaar_logo_coloured)
        actionBar?.setDisplayUseLogoEnabled(true)

        actionBar?.show()



        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        searchView = view.findViewById(R.id.searchViewId)
        searchView.queryHint = Html.fromHtml("<font color = #636363>" + resources.getString(R.string.search_long) + "</font>");
        recycler_view = view.findViewById(R.id.recycler_view)
        setupRecyclerView()
        listViewModel.products.observe(viewLifecycleOwner){
            adapter.setData(listViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }




        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapter.filter.filter(p0)
                searchView.clearFocus()

                //adapter.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                //adapter.notifyDataSetChanged()
                return true

            }

        })




        //tempArrayList = arrayListOf<Product>()
        //tempArrayList.addAll(listViewModel.products)
        return view
    }

    private fun setupRecyclerView(){


        adapter = DataAdapter(ArrayList<Product>(), this.requireContext(), this, this) //here we
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
        val clickedItem = listViewModel.products.value!![position]
        ProductDetailData.changeWholeProduct(clickedItem)
        findNavController().navigate(R.id.detailsFragment)
    }

    override fun onAddOrderButtonClick(position: Int) {
        val clickedItem = listViewModel.products.value!![position]

        if(clickedItem.username == userData.getUsername())
        {
            Toast.makeText(this.context, "You cannot buy your own product", Toast.LENGTH_SHORT).show()
        }
        else
        {
            lifecycleScope.launch {
                val msg = listViewModel.addOrder(
                    title = clickedItem.title,
                    description = clickedItem.description,
                    price_per_unit = clickedItem.price_per_unit,
                    units = clickedItem.units,
                    owner_username = clickedItem.username
                )


            }









        }


    }

    override fun onItemLongClick(position: Int) {
    }

    override fun onResume() {

        if (firstResume)
        {
            firstResume = false
        }else
        {
            listViewModel.refresh()
        }
        super.onResume()

    }





}
