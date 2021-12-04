package project.bazaar.model

import android.graphics.Bitmap
import java.io.File

import android.graphics.BitmapFactory
import java.io.FileInputStream


object userData
{
    private lateinit var username : String
    private lateinit var email : String
    private lateinit var phoneNumber : String
    private lateinit var profilePicture : File

    fun changeData(username : String, email : String, phoneNumber : String)
    {
        this.email = email
        this.phoneNumber = phoneNumber
        this.username = username
    }

    fun changeDataAndImage(username : String, email : String, phoneNumber : String, profilePicture : File)
    {
        this.email = email
        this.phoneNumber = phoneNumber
        this.username = username
        this.profilePicture = profilePicture
    }

    fun getUsername() : String
    {
        return this.username
    }

    fun getEmail() : String
    {
        return this.email
    }

    fun getPhoneNumber() : String
    {
        return this.phoneNumber
    }

    fun getUserImage() : File
    {
        return this.profilePicture
    }

    fun getUserImageAsBitmap(): Bitmap? {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeStream(FileInputStream(profilePicture), null, options)
    }


}