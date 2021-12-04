package project.bazaar.model

import android.app.Application
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.engine.Resource
import com.squareup.moshi.JsonClass
import project.bazaar.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


//import com.google.gson.annotations.SerializedName

/*
Data class for the information of user.
+
LoginRequest data and LoginResponse data in JSON format for the rest api
 */

//, var userImage: File = getDefaultFile()
data class User(var username: String="", var password: String="", var email: String="", var phone_number: String="")


fun getDefaultFile(): File {

    val drawableResourceId = R.drawable.ic_tile_1
    val bitmap = BitmapFactory.decodeResource(Resources.getSystem(), drawableResourceId)
    val fileNameToSave = "profilePicture"
    val file = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileNameToSave)
    file.createNewFile()
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
    val bitmapdata = bos.toByteArray()
    val fos = FileOutputStream(file)
    fos.write(bitmapdata)
    fos.flush()
    fos.close()

    return file

}

@JsonClass(generateAdapter = true)
data class LoginRequest (
    var username: String,
    var password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse (
    var username: String,
    var email: String,
    var phone_number: Int,
    var token: String,
    var creation_time: Long,
    var refresh_time: Long
)
@JsonClass(generateAdapter = true)
data class RegisterRequest(
    var username: String,
    var password: String,
    var email: String,
    var phone_number: String


)

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    var code : Int,
    var message : String,
    var creation_time: Long
)

@JsonClass(generateAdapter = true)
data class ResetPasswordRequest(
    var username: String,
    var email: String
)

@JsonClass(generateAdapter = true)
data class GeneralResponse(
    var code : Int,
    var message : String,
    var timestamp: Long
)


@JsonClass(generateAdapter = true)
data class UpdateUserDataRequest(
    var username: String,
    var email: String,
    var phone_number: String
    //var userImage : File
)

@JsonClass(generateAdapter = true)
data class UpdateUserDataResponse(
    var code : Int,
    var updatedData: updatedData,
    var timestamp: Long

)

@JsonClass(generateAdapter = true)
data class updatedData(
    var username: String,
    var phone_number: Int,
    var email: String,
    var is_activated : Boolean,
    var creation_time: Long,
    var token: String
)






// GSon converter
//data class LoginRequest (
//    @SerializedName("username")
//    var username: String,
//
//    @SerializedName("password")
//    var password: String
//)
//
//
//data class LoginResponse (
//    @SerializedName("username")
//    var username: String,
//
//    @SerializedName("email")
//    var email: String,
//
//    @SerializedName("phone_number")
//    var phone_number: Int,
//
//    @SerializedName("token")
//    var token: String,
//
//    @SerializedName("creation_time")
//    var creation_time: Long,
//
//    @SerializedName("refresh_time")
//    var refresh_time: Long
//)