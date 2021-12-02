package project.bazaar

import android.app.ActionBar.DISPLAY_SHOW_CUSTOM
import android.app.ActionBar.DISPLAY_USE_LOGO
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.*
import android.view.View.inflate
import androidx.appcompat.app.ActionBar
import androidx.core.view.forEach
import androidx.navigation.NavController
import com.bumptech.glide.load.engine.Resource
import org.xmlpull.v1.XmlPullParser


class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNav : BottomNavigationView
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        //supportActionBar?.hide()
        val actionBar: android.app.ActionBar? = actionBar
        actionBar?.setDisplayShowTitleEnabled(false)






        //setSupportActionBar(toolbar)
        bottomNav = findViewById(R.id.bottomNavigationBarId)
        navController = findNavController(R.id.nav_host_fragment)

        findViewById<BottomNavigationView>(R.id.bottomNavigationBarId)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.forgotPasswordFragment -> hideBottomNav()
                R.id.loginFragment -> hideBottomNav()
                R.id.registerFragment -> hideBottomNav()
                else -> showBottomNav()
            }


        }


        bottomNav.setupWithNavController(navController)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.nav_timeline -> navController.navigate(R.id.listFragment)
                R.id.nav_myMarket -> navController.navigate(R.id.myMarketFragment)
                R.id.nav_myFares -> navController.navigate(R.id.myFaresFragment)
            }
            true
        }







        // Up navigation
        NavigationUI.setupActionBarWithNavController(this, navController)





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.top_actionbar_menu, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId)
        {
            R.id.profileIcon -> {
                navController.navigate(R.id.profileFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        //val navController = findNavController(R.id.nav_host_fragment)


        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
        private fun showBottomNav() {
            bottomNav.visibility = View.VISIBLE

        }

        private fun hideBottomNav() {
            bottomNav.visibility = View.GONE

        }


}


