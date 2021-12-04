package project.bazaar.fragments



import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import project.bazaar.R
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.LoginViewModel
import project.bazaar.viewmodels.LoginViewModelFactory
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity




/*
first phase of the program. The user has to log in to the system,
otherwise he cannot use the Bazaar
 */

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var editText1 : EditText
    private lateinit var editText2 : EditText
    private lateinit var button : Button
    private lateinit var signupButton : Button
    private lateinit var forgotPasswordClick : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        editText1 = view.findViewById(R.id.edittext_name_login_fragment)
        editText2 = view.findViewById(R.id.edittext_password_login_fragment)
        button = view.findViewById(R.id.button_login_fragment)
        signupButton = view.findViewById(R.id.sign_up_button_login_fragment)
        forgotPasswordClick = view.findViewById(R.id.forgot_Password_click)



        // Onclick listener for the textview click here to send new password
        (activity as AppCompatActivity?)!!.supportActionBar?.hide()
        forgotPasswordClick.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        button.setOnClickListener {
            loginViewModel.user.value.let {
                if (it != null) {
                    it.username = editText1.text.toString()
                }
                if (it != null) {
                    it.password = editText2.text.toString()
                }
            }
            lifecycleScope.launch {
                if(!loginViewModel.login())
                {
                    editText1.setText("")
                    editText2.setText("")
                    requireContext().hideKeyboard(view)
                }

            }


        }
        loginViewModel.token.observe(viewLifecycleOwner){
            Log.d("xxx", "navigate to list")
            findNavController().navigate(R.id.action_loginFragment_to_listFragment)
        }
        return view
    }



    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}



