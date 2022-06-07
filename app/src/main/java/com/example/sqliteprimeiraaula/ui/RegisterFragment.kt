package com.example.sqliteprimeiraaula.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sqliteprimeiraaula.R
import com.example.sqliteprimeiraaula.viewmodel.AccessViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var accessViewModel : AccessViewModel
    private lateinit var registerButton : Button
    private lateinit var txtUser : EditText
    private lateinit var txtPassword : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accessViewModel = ViewModelProvider(this)[AccessViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButton = view.findViewById(R.id.btnRegister)
        txtUser =  view.findViewById(R.id.txtUser)
        txtPassword =  view.findViewById(R.id.txtPassword)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners(){
        registerButton.setOnClickListener{
            val name = txtUser.text.toString()
            val password = txtPassword.text.toString()
            accessViewModel.onUserRequestRegister(requireContext(),name,password)
        }
    }

    private fun setupObservers(){
        accessViewModel.onUserRequestToRegisterLiveData.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
            }
        }
    }

}