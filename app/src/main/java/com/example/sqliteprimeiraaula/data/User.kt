package com.example.sqliteprimeiraaula.data

import android.os.Build
import androidx.annotation.RequiresApi

data class User(var name : String, var pwd : String) {

    fun validaSenha():Boolean {
        return !pwd.isEmpty() && pwd.length >= 8
                && pwd.any{it.isUpperCase() && it.isLowerCase()}
    }
}