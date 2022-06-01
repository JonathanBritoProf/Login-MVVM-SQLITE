package com.example.sqliteprimeiraaula.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sqliteprimeiraaula.R
import com.example.sqliteprimeiraaula.viewmodel.AcessViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    lateinit var acessViewModel : AcessViewModel
    lateinit var registerButton : Button
    lateinit var txtUser : EditText
    lateinit var txtPassword : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        acessViewModel = ViewModelProvider(this).get(AcessViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButton = view.findViewById(R.id.btnRegister)
        txtUser =  view.findViewById(R.id.txtUser)
        txtPassword =  view.findViewById(R.id.txtPassword)
        setupListeners()
        setupObservers()
    }

    fun setupListeners(){
        registerButton.setOnClickListener{
            var name = txtUser.text.toString()
            var password = txtPassword.text.toString()
            acessViewModel.OnUserRequestRegister(requireContext(),name,password)
        }
    }

    fun setupObservers(){
        acessViewModel.onUserRequestToRegisterLiveData.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
            }
        }
    }

}