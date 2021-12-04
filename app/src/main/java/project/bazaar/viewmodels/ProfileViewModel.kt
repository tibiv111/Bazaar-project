package project.bazaar.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.bazaar.Bazaar
import project.bazaar.model.UpdateUserDataRequest
import project.bazaar.model.User
import project.bazaar.model.userData
import project.bazaar.model.userData.changeData
import project.bazaar.repository.Repository

import project.bazaar.model.userData.changeDataAndImage

class ProfileViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

    suspend fun updateUserData(): Boolean {
        var isSuccessful: Boolean = false


        val request =
            UpdateUserDataRequest(
                phone_number = user.value!!.phone_number,
                email = user.value!!.email.toString(),
                username = user.value!!.username.toString()
            )


        try {
            val result = repository.updateUserData(request)

            //changeDataAndImage(result.username, result.email, result.phone_number, user.value!!.userImage)

            Bazaar.token = result.updatedData.token
            token.value = result.updatedData.token
            changeData(result.updatedData.username, result.updatedData.email, "0" + result.updatedData.phone_number.toString())
            isSuccessful = true
            Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()

        }

        return isSuccessful
    }
}
