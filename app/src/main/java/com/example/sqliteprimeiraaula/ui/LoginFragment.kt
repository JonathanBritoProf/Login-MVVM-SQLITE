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
import com.example.sqliteprimeiraaula.viewmodel.AcessViewModel
import com.google.android.gms.common.SignInButton


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var acessViewModel: AcessViewModel
    private lateinit var usernameEditText : EditText
    private lateinit var passwordEditText : EditText
    private lateinit var loginButton : Button
    private lateinit var registerButton : Button
    private lateinit var googleSignInBtn : SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        acessViewModel = ViewModelProvider(this).get(AcessViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernameEditText = view.findViewById(R.id.username)
        passwordEditText = view.findViewById(R.id.password)
        loginButton = view.findViewById(R.id.login)
        registerButton = view.findViewById(R.id.registrarBtn)
        googleSignInBtn =  view.findViewById(R.id.sign_in_button)
        setupListeners()
        setupObservers()
    }

    fun setupListeners(){
        loginButton.setOnClickListener{
            var name = usernameEditText.text.toString()
            var password = passwordEditText.text.toString()
            acessViewModel.OnUserRequestLogin(requireContext(),name,password)
        }

        registerButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment2_to_registerFragment)
        }

        googleSignInBtn.setOnClickListener {
            acessViewModel.OnUserRequestGoogleSignIn(requireActivity())
        }
    }

    fun setupObservers(){
        acessViewModel.onUserRequestToLoginLiveData.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_loginFragment2_to_mainFragment2)
            }
        }

        acessViewModel.onUserRequestToGoogleSignInLiveData.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_loginFragment2_to_mainFragment2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    if(requestCode == acessViewModel.GOOGLE_REQUEST_CODE){
        acessViewModel.OnGoogleSignInSucess(requireContext())
    }
    }

}
