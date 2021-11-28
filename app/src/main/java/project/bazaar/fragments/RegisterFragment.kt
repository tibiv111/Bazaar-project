package project.bazaar.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import project.bazaar.R
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.LoginViewModel
import project.bazaar.viewmodels.LoginViewModelFactory
import project.bazaar.viewmodels.RegisterViewModel
import project.bazaar.viewmodels.RegisterViewModelFactory


class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = RegisterViewModelFactory(this.requireContext(), Repository())
        registerViewModel = ViewModelProvider(this@RegisterFragment, factory).get(RegisterViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val username : TextView = view.findViewById(R.id.username_data_edittext_register_fragment)
        val password : TextView = view.findViewById(R.id.password_data_textview)
        val email : TextView = view.findViewById(R.id.edittext_email_data)
        val phone_number : TextView = view.findViewById(R.id.phone_number_register_fragment)
        val registerButton : Button = view.findViewById(R.id.button_register_fragment)
        val loginClickable : TextView = view.findViewById(R.id.loginClickable)

        loginClickable.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        registerButton.setOnClickListener {
            registerViewModel.user.value.let {
                if (it != null) {
                    it.username = username.text.toString()
                }
                if (it != null) {
                    it.password = password.text.toString()
                }
                if (it != null) {
                    it.email = email.text.toString()
                }
                if (it != null) {
                    it.phone_number = phone_number.text.toString()
                }

            }
            lifecycleScope.launch {
                if(registerViewModel.register())
                {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }else
                {
                    requireContext().hideKeyboard(view)
                }
            }

            //Toast.makeText(this.context,"You have registered!", Toast.LENGTH_SHORT).show()

        }



        return view
    }
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}