package project.bazaar.utils


import android.content.Context
import android.content.SharedPreferences
import project.bazaar.R

// Not used!!!
class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        var TOKEN = "token"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }


    fun fetchAuthToken(): String? {
        return prefs.getString(TOKEN, null)
    }
}