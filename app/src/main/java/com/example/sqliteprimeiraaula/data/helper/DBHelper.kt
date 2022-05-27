package com.example.sqliteprimeiraaula.data.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqliteprimeiraaula.data.User

class DBHelper (context: Context) : SQLiteOpenHelper(context,"userDB",null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS USERS(USERID PRIMARY KEY AUTOINCREMENT," +
                "NOME TEXT, PWD TEXT) ")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun validateUser(user: User) : Boolean {
    var listaDeUser = readAllUsers()
        for(currentUser in listaDeUser){
         if(currentUser.name.equals(user.name) && currentUser.pwd.equals(user.pwd)){
             return true
         }
        }
        return false
    }

    fun readAllUsers() : ArrayList<User>{
        var users = ArrayList<User>()
        var db = readableDatabase
        var queryResult = db.rawQuery("SELECT * FROM USERS",null)
        var nome : String
        var password : String
        if(queryResult.moveToNext()){
            while (!queryResult.isAfterLast ){
                nome = queryResult.getString(1)
                password = queryResult.getString(2)
                users.add(User(nome,password))
                queryResult.moveToNext()
            }
        }
        return users
    }
}