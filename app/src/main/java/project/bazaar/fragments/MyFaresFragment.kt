package project.bazaar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import project.bazaar.R
import project.bazaar.adapters.ViewPagerAdapterOS


class MyFaresFragment : Fragment() {



    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter : ViewPagerAdapterOS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //here I set up the required actionbar for my fragment
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = "My Fares" // ez a title
        actionBar?.setDisplayShowHomeEnabled(true) // ez nem tudom már
        actionBar?.setDisplayHomeAsUpEnabled(true) //Ez a vissza jel
        //actionBar?.setLogo(R.drawable.ic_bazaar_logo_coloured)
        actionBar?.setDisplayUseLogoEnabled(false) // Ez a logo a title mellett

        actionBar?.show()



        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_my_fares, container, false)
        val fragmentList = arrayListOf<Fragment>(
            OngoingSalesFragment(),
            OngoingOrdersFragment()
        )
        tabLayout = view.findViewById(R.id.tabLayout)

        adapter = ViewPagerAdapterOS(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        viewPager2 = view.findViewById(R.id.viewPager2)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager2){tab, position ->
            when(position)
            {
                0 ->{
                    tab.text="Ongoing Sales"

                }
                1 ->
                {
                    tab.text= "Ongoing Orders"
                }
            }
        }.attach()
        return view
    }



}