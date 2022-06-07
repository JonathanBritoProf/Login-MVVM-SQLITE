package com.example.sqliteprimeiraaula.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.sqliteprimeiraaula.data.User
import com.example.sqliteprimeiraaula.data.database.AppDatabase
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

class AccessViewModel : ViewModel() {

    val GOOGLE_REQUEST_CODE = 1000
    private val EMAIL = "email"

    private lateinit var gso : GoogleSignInOptions
    private lateinit var gsc : GoogleSignInClient

    //cria o objeto user
    private val user = User(null,"","")

    //cria um mutable livedata de boolean
    private val onUserRequestToLogin = MutableLiveData<Boolean>()
    private val onUserRequestToRegister = MutableLiveData<Boolean>()
    private val onUserRequestToGoogleSignIn = MutableLiveData<Boolean>()
    private val onUserRequestToFacebookSignIn = MutableLiveData<Boolean>()

    //recebe o valor do mutable Live Data no observer
    val onUserRequestToLoginLiveData : LiveData<Boolean> = onUserRequestToLogin
    val onUserRequestToRegisterLiveData : LiveData<Boolean> = onUserRequestToRegister
    val onUserRequestToGoogleSignInLiveData : LiveData<Boolean> = onUserRequestToGoogleSignIn
    val onUserRequestToFacebookSignInLiveData : LiveData<Boolean> = onUserRequestToFacebookSignIn


    fun onUserRequestLogin(context: Context, nome : String, password : String){
        user.name = nome
        user.pwd = password
        viewModelScope.launch {
            val db = Room.databaseBuilder(context, AppDatabase::class.java,"pessoaDB").allowMainThreadQueries().build()
            val dao = db.UserDAO()
            if(user.validaSenha() && dao.findByName(nome,password) != null){
                onUserRequestToLogin.value = true
            }else{
                Toast.makeText(context,"Verifique os dados digitados", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun onUserRequestRegister(context: Context, nome : String, password : String){
        user.name = nome
        user.pwd = password
        viewModelScope.launch {
            val db = Room.databaseBuilder(context, AppDatabase::class.java,"pessoaDB").allowMainThreadQueries().build()
            val dao = db.UserDAO()
            if(user.validaSenha()){
                dao.insert(user)
                Toast.makeText(context,"Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show()
                onUserRequestToRegister.value = true
            }else{
                Toast.makeText(context,"Verifique os dados digitados", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun onUserRequestGoogleSignIn(activity: Activity){
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc =  GoogleSignIn.getClient(activity,gso)

        val intent = gsc.signInIntent
        activity.startActivityForResult(intent,GOOGLE_REQUEST_CODE)
    }

     fun onGoogleSignInSucess(context: Context){
         val account = GoogleSignIn.getLastSignedInAccount(context)
         if(account != null) {
             onUserRequestToGoogleSignIn.value = true
         }
     }

    fun onUserRequestFacebookSignIn(loginButton: LoginButton){
        val callbackManager = CallbackManager.Factory.create()
        loginButton.setReadPermissions(listOf(EMAIL))

        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                onUserRequestToFacebookSignIn.value =  true
            }

            override fun onCancel() {
                onUserRequestToFacebookSignIn.value = false
            }

            override fun onError(exception: FacebookException) {
                Log.e("TAG","OnFaceAuthError",exception)
                onUserRequestToFacebookSignIn.value =  false
            }
        })

    }


}