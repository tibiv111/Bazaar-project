package project.bazaar.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.bazaar.model.ResetPasswordRequest
import project.bazaar.model.User
import project.bazaar.repository.Repository

class ResetPasswordViewModel(val context: Context, val repository: Repository) : ViewModel() {
    //var token: MutableLiveData<String> = MutableLiveData()
    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

    suspend fun resetPassword(): Boolean {
        var isSucessful: Boolean = false
        val request =
            ResetPasswordRequest(username = user.value!!.username, email = user.value!!.email)
        try {
            val result = repository.resetPassword(request)


            Toast.makeText(this.context, "Password reset email sent!", Toast.LENGTH_SHORT).show()
            isSucessful = true
            //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

            //Log.d("xxx", "Bazaar - token:  ${Bazaar.token}")

        } catch (e: Exception) {
            Log.d("xxx", "ResetPasswordViewModel - exception: ${e.toString()}")
            Toast.makeText(this.context, "Invalid credentials", Toast.LENGTH_SHORT).show()
        }

        return isSucessful

    }
}
