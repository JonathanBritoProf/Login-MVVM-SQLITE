package com.example.sqliteprimeiraaula.ui

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sqliteprimeiraaula.R
import com.example.sqliteprimeiraaula.viewmodel.AcessViewModel


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var acessViewModel: AcessViewModel
    private lateinit var usernameEditText : EditText
    private lateinit var passwordEditText : EditText
    private lateinit var loginButton : Button
    private lateinit var registerButton : Button

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
    }

    fun setupObservers(){
        acessViewModel.onUserRequestToLoginLiveData.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_loginFragment2_to_mainFragment2)
            }
        }
    }

}
