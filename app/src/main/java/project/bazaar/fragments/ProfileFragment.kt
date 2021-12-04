package project.bazaar.fragments

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import project.bazaar.model.User
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import project.bazaar.R
import project.bazaar.model.userData
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.LoginViewModel
import project.bazaar.viewmodels.LoginViewModelFactory
import project.bazaar.viewmodels.ProfileViewModel
import project.bazaar.viewmodels.ProfileViewModelFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception




class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profilePicture: ImageView
    private var profilePictureFile : File? = null
    private lateinit var email: EditText
    private lateinit var username: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var saveChangesButton : Button
    private lateinit var bitmap : Bitmap
    val PICK_IMAGE_REQUEST_CODE = 100;
    val user = userData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ProfileViewModelFactory(this.requireContext(), Repository())
        profileViewModel = ViewModelProvider(this@ProfileFragment, factory).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = "Profile"
        actionBar?.setDisplayShowHomeEnabled(true)
        //actionBar?.setDisplayHomeAsUpEnabled(false)
        //actionBar?.setLogo(R.drawable.ic_bazaar_logo_coloured)
        actionBar?.setDisplayUseLogoEnabled(false)

        actionBar?.show()


        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        profilePicture = view.findViewById(R.id.profileImageView)
        email = view.findViewById(R.id.profileEmailTextview)
        username = view.findViewById(R.id.username_profile_fragment)
        phoneNumber = view.findViewById(R.id.phone_number_profile_fragment)
        saveChangesButton = view.findViewById(R.id.saveChangesButton)

        //profilePicture.setImageBitmap(user.getUserImageAsBitmap())
        email.setText(user.getEmail())
        email.isEnabled = false
        username.setText(user.getUsername())
        phoneNumber.setText(user.getPhoneNumber())








        profilePicture.setOnClickListener {
            pickImageGallery()
            /*
            Press Alt + Enter
             */
        }


        saveChangesButton.setOnClickListener {
            var isThereNewPicture = false
            profileViewModel.user.value.let {

                /*
                if (profilePictureFile != null && it != null)
                {
                    isThereNewPicture = true
                    it.userImage = profilePictureFile!!
                }

                 */
                if (it != null) {

                    it.phone_number = phoneNumber.text.toString()
                    it.email = email.text.toString()
                    it.username = username.text.toString()

                }



            }
            lifecycleScope.launch {
                profileViewModel.updateUserData()
            }
        }




        return view
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            profilePicture.setImageURI(data?.data)
                /*
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, data?.data)
                    profilePicture.setImageBitmap(bitmap)
                    val fileNameToSave = "profilePicture"
                    profilePictureFile = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileNameToSave)
                    profilePictureFile!!.createNewFile()
                    val bos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
                    val bitmapdata = bos.toByteArray()
                    val fos = FileOutputStream(profilePictureFile)
                    fos.write(bitmapdata)
                    fos.flush()
                    fos.close()
                }catch (e:Exception)
                {
                    Toast.makeText(context, "Error setting profile picture", Toast.LENGTH_SHORT).show()
                }

                 */



        }
    }


}