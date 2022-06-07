package com.example.sqliteprimeiraaula.ui

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sqliteprimeiraaula.R
import com.example.sqliteprimeiraaula.viewmodel.AccessViewModel
import com.facebook.login.widget.LoginButton
import com.google.android.gms.common.SignInButton


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var accessViewModel: AccessViewModel
    private lateinit var usernameEditText : EditText
    private lateinit var passwordEditText : EditText
    private lateinit var loginButton : Button
    private lateinit var registerButton : Button
    private lateinit var googleSignInBtn : SignInButton
    private lateinit var facebookBtn : LoginButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accessViewModel = ViewModelProvider(this)[AccessViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernameEditText = view.findViewById(R.id.username)
        passwordEditText = view.findViewById(R.id.password)
        loginButton = view.findViewById(R.id.login)
        registerButton = view.findViewById(R.id.registrarBtn)
        googleSignInBtn =  view.findViewById(R.id.sign_in_button)
        facebookBtn =  view.findViewById(R.id.login_button)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners(){
        loginButton.setOnClickListener{
            val name = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            accessViewModel.onUserRequestLogin(requireContext(),name,password)
        }

        registerButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment2_to_registerFragment)
        }

        googleSignInBtn.setOnClickListener {
            accessViewModel.onUserRequestGoogleSignIn(requireActivity())
        }

        facebookBtn.setOnClickListener {
            accessViewModel.onUserRequestFacebookSignIn(facebookBtn)
        }
    }

    private fun setupObservers(){
        accessViewModel.onUserRequestToLoginLiveData.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment2_to_mainFragment2)
            }
        }

        accessViewModel.onUserRequestToGoogleSignInLiveData.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment2_to_mainFragment2)
            }
        }

        accessViewModel.onUserRequestToFacebookSignInLiveData.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment2_to_mainFragment2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    if(requestCode == accessViewModel.GOOGLE_REQUEST_CODE){
        accessViewModel.onGoogleSignInSucess(requireContext())
    }
    }

}
