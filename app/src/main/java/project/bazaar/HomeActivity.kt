package project.bazaar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.*
import androidx.navigation.NavController


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


