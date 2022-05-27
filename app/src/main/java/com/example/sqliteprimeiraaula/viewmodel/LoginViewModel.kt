package com.example.sqliteprimeiraaula.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sqliteprimeiraaula.data.User
import com.example.sqliteprimeiraaula.data.helper.DBHelper

class LoginViewModel() : ViewModel() {

    //cria o objeto user
    private val user = User("","")

    //cria um mutable livedata de boolean
    private val onUserRequestToLogin = MutableLiveData<Boolean>()

    //recebe o valor do mutable Live Data no observer
    val onUserRequestToLoginLiveData : LiveData<Boolean> = onUserRequestToLogin

    fun OnUserRequestLogin(context: Context, nome : String, password : String){
        user.name = nome
        user.pwd = password
        var helper = DBHelper(context)
        if(user.validaSenha() && helper.validateUser(user)){
            onUserRequestToLogin.value = true
        }else{
            Toast.makeText(context,"Verifique os dados digitados", Toast.LENGTH_LONG).show()
        }
    }


}