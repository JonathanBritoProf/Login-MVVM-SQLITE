package com.example.sqliteprimeiraaula.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)var id : Int?,
    @ColumnInfo(name = "NAME")var name : String,
    @ColumnInfo(name = "PWD")var pwd : String) {

    fun validaSenha():Boolean {
        return pwd.length >= 6
             //   && pwd.any{it.isUpperCase() && it.isLowerCase()}
    }
}