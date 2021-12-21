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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController


/*
List fragment is created for the recycleview to populate
 */
class ListFragment : Fragment() , DataAdapter.OnItemClickListener, DataAdapter.OnItemLongClickListener {
    lateinit var listViewModel: ListViewModel

    private lateinit var recycler_view: RecyclerView
    private lateinit var adapter: DataAdapter
    private lateinit var tempArrayList : ArrayList<Product>
    private lateinit var searchView : androidx.appcompat.widget.SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory(Repository())
        listViewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)
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
        findNavController().navigate(R.id.detailsFragment)
    }

    override fun onItemLongClick(position: Int) {
//        TODO("Not yet implemented")
    }

    override fun onResume() {
        listViewModel.refresh()
        super.onResume()

    }





}
