package com.example.sqliteprimeiraaula.ui

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.sqliteprimeiraaula.R
import com.example.sqliteprimeiraaula.viewmodel.LoginViewModel


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var usernameEditText : EditText
    private lateinit var passwordEditText : EditText
    private lateinit var loginButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernameEditText = view.findViewById(R.id.username)
        passwordEditText = view.findViewById(R.id.password)
        loginButton = view.findViewById(R.id.login)
        setupListeners()

    }

    fun setupListeners(){
        loginButton.setOnClickListener{
            var name = usernameEditText.text.toString()
            var password = passwordEditText.text.toString()
            loginViewModel.OnUserRequestLogin(requireContext(),name,password)
        }
    }

    fun setupObservers(){
        loginViewModel.onUserRequestToLoginLiveData.observe(this,{

        })
    }

}
