package project.bazaar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.bazaar.R
import project.bazaar.adapters.DataAdapter
import project.bazaar.model.Product
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.ListViewModel
import project.bazaar.viewmodels.MyFaresViewModelFactory
import project.bazaar.viewmodels.MyFaresViewModel


class MyFaresFragment : Fragment(), DataAdapter.OnItemClickListener, DataAdapter.OnItemLongClickListener {

    private lateinit var myFaresViewModel: MyFaresViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var adapter: DataAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyFaresViewModelFactory(Repository())
        myFaresViewModel = ViewModelProvider(this, factory).get(MyFaresViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //here I set up the required actionbar for my fragment
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = "My Fares"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        //actionBar?.setLogo(R.drawable.ic_bazaar_logo_coloured)
        actionBar?.setDisplayUseLogoEnabled(false)

        actionBar?.show()



        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_my_fares, container, false)

        recycler_view = view.findViewById(R.id.recycler_view2)
        setupRecyclerView()
        myFaresViewModel.products.observe(viewLifecycleOwner){
            adapter.setData(myFaresViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }
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
        val clickedItem = myFaresViewModel.products.value!![position]
        //findNavController().navigate(R.id.detailsFragment)
        TODO()

    }

    override fun onItemLongClick(position: Int) {
//        TODO("Not yet implemented")
    }

}