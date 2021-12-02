package project.bazaar.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import okhttp3.MediaType
import project.bazaar.Bazaar
import project.bazaar.R

import project.bazaar.model.RegisterRequest
import project.bazaar.model.User
import project.bazaar.repository.Repository
import java.io.File

class RegisterViewModel(val context: Context, val repository: Repository) : ViewModel() {
    //var token: MutableLiveData<String> = MutableLiveData()
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

    suspend fun register() : Boolean
    {
        var isSucessful : Boolean = false
        val request =
            RegisterRequest(username = user.value!!.username, password = user.value!!.password,
                            email = user.value!!.email, phone_number = user.value!!.phone_number.toString())
        try {
            val result = repository.register(request)


            Toast.makeText(this.context, "Registration successful!", Toast.LENGTH_SHORT).show()
            isSucessful = true
                //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

            //Log.d("xxx", "Bazaar - token:  ${Bazaar.token}")

        }catch (e: Exception){

            Log.d("xxx", "RegisterViewModel - exception: ${e.toString()}")
            if ( "300" in e.toString())
            {
                Toast.makeText(this.context, "Please complete all the fields!", Toast.LENGTH_SHORT).show()
            }
            if ("301" in e.toString())
            {
                Toast.makeText(this.context, "Wrong file format!", Toast.LENGTH_SHORT).show()
            }
            if ("302" in e.toString())
            {
                Toast.makeText(this.context, "Email incorrect!", Toast.LENGTH_SHORT).show()
            }
            if ("303" in e.toString())
            {
                Toast.makeText(this.context, "Username, email or phone number already used!", Toast.LENGTH_SHORT).show()
            }
        }

        return isSucessful
    }




}