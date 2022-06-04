package com.example.sqliteprimeiraaula.viewmodel

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.sqliteprimeiraaula.data.User
import com.example.sqliteprimeiraaula.data.database.AppDatabase
import com.example.sqliteprimeiraaula.data.helper.DBHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AcessViewModel() : ViewModel() {

    val GOOGLE_REQUEST_CODE = 1000

    private lateinit var gso : GoogleSignInOptions
    private lateinit var gsc : GoogleSignInClient

    //cria o objeto user
    private val user = User(null,"","")

    //cria um mutable livedata de boolean
    private val onUserRequestToLogin = MutableLiveData<Boolean>()
    private val onUserRequestToRegister = MutableLiveData<Boolean>()
    private val onUserRequestToGoogleSignIn = MutableLiveData<Boolean>()

    //recebe o valor do mutable Live Data no observer
    val onUserRequestToLoginLiveData : LiveData<Boolean> = onUserRequestToLogin
    val onUserRequestToRegisterLiveData : LiveData<Boolean> = onUserRequestToRegister
    val onUserRequestToGoogleSignInLiveData : LiveData<Boolean> = onUserRequestToGoogleSignIn

    fun OnUserRequestLogin(context: Context, nome : String, password : String){
        user.name = nome
        user.pwd = password
        viewModelScope.launch {
            var db = Room.databaseBuilder(context, AppDatabase::class.java,"pessoaDB").allowMainThreadQueries().build()
            var dao = db.UserDAO()
            if(user.validaSenha() && dao.findByName(nome,password) != null){
                onUserRequestToLogin.value = true
            }else{
                Toast.makeText(context,"Verifique os dados digitados", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun OnUserRequestRegister(context: Context, nome : String, password : String){
        user.name = nome
        user.pwd = password
        viewModelScope.launch {
            var db = Room.databaseBuilder(context, AppDatabase::class.java,"pessoaDB").allowMainThreadQueries().build()
            var dao = db.UserDAO()
            if(user.validaSenha()){
                dao.insert(user)
                Toast.makeText(context,"Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show()
                onUserRequestToRegister.value = true
            }else{
                Toast.makeText(context,"Verifique os dados digitados", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun OnUserRequestGoogleSignIn(activity: Activity){
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc =  GoogleSignIn.getClient(activity,gso)

        var intent = gsc.signInIntent
        activity.startActivityForResult(intent,GOOGLE_REQUEST_CODE)
    }

     fun OnGoogleSignInSucess(context: Context){
         var account = GoogleSignIn.getLastSignedInAccount(context)
         if(account != null) {
             onUserRequestToGoogleSignIn.value = true
         }
     }


}