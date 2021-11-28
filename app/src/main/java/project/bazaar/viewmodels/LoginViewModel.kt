package project.bazaar.viewmodels


import android.content.Context
import android.text.TextUtils.substring
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import project.bazaar.Bazaar
import project.bazaar.model.LoginRequest
import project.bazaar.model.User
import project.bazaar.repository.Repository
import project.bazaar.utils.SessionManager
import kotlinx.coroutines.launch
import java.nio.file.Files.find


class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

//    fun login() {
//        viewModelScope.launch {
//            val request =
//                LoginRequest(username = user.value!!.username, password = user.value!!.password)
//            try {
//                val result = repository.login(request)
//                MyApplication.token = result.token
//                token.value = result.token
//                Log.d("xxx", "MyApplication - token:  ${MyApplication.token}")
//            }catch(e: Exception){
//                Log.d("xxx", "MainViewModel - exception: ${e.toString()}")
//            }
//        }
//    }

    suspend fun login() : Boolean{
        var isSuccessful : Boolean = false
        val request =
            LoginRequest(username = user.value!!.username, password = user.value!!.password)
        try {
            val result = repository.login(request)

            Bazaar.token = result.token
            token.value = result.token
            Toast.makeText(this.context, "Logged in!", Toast.LENGTH_SHORT).show()
            Log.d("xxx", "Bazaar - token:  ${Bazaar.token}")
            isSuccessful = true
        } catch (e: Exception) {
            Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
            if ( "300" in e.toString())
            {
                Toast.makeText(this.context, "Username or password missing!", Toast.LENGTH_SHORT).show()
            }
            if ("301" in e.toString())
            {
                Toast.makeText(this.context, "Account not activated!", Toast.LENGTH_SHORT).show()
            }
            if ("302" in e.toString())
            {
                Toast.makeText(this.context, "No such user!", Toast.LENGTH_SHORT).show()
            }
        }
        return isSuccessful
    }
}


