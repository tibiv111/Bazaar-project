package project.bazaar.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import project.bazaar.R
import project.bazaar.repository.Repository
import project.bazaar.viewmodels.RegisterViewModel
import project.bazaar.viewmodels.RegisterViewModelFactory
import project.bazaar.viewmodels.ResetPasswordViewModel
import project.bazaar.viewmodels.ResetPasswordViewModelFactory


class ForgotPasswordFragment : Fragment() {

    private lateinit var resetPasswordViewModel: ResetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ResetPasswordViewModelFactory(this.requireContext(), Repository())
        resetPasswordViewModel = ViewModelProvider(this@ForgotPasswordFragment, factory).get(ResetPasswordViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)
        val email : TextView = view.findViewById(R.id.forgot_password_textedit_forgotpassword_fragment)
        val username : TextView = view.findViewById(R.id.forgot_password_textedit_username)
        val emailMeButton : Button = view.findViewById(R.id.button_forgot_password_email_me)


        emailMeButton.setOnClickListener {
            resetPasswordViewModel.user.value.let {
                if (it != null) {
                    it.username = username.text.toString()
                    it.email = email.text.toString()
                }
            }
            lifecycleScope.launch {
                if (resetPasswordViewModel.resetPassword()) {
                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                }else
                {
                    requireContext().hideKeyboard(view)
                }

            }
        }



        return view
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}