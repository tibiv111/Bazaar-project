package project.bazaar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

/*
MainActivity has been transformed into a splash screen.
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // We hide the action bar so it won't be visible on the splash screen
        supportActionBar?.hide()

        // We create an intent, but with the help of Handler we delay it by 3000 ms (3 seconds)
        // Handler does not block the main activity it only delays the code inside it,
        // so the code that comes after the handler will run as it normally would.
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            Log.d("Handler", "This is the handler code")
            finish()
        }, 3000)
        Log.d("Handler", "This should run before the handler code")
    }


}