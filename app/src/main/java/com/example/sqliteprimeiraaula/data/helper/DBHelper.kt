package com.example.sqliteprimeiraaula.data.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqliteprimeiraaula.data.User

class DBHelper (context: Context) : SQLiteOpenHelper(context,"userDB",null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS USERS(USERID PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT, PWD TEXT) ")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun validateUser(user: User) : Boolean {
    var listaDeUser = readAllUsers()
        return listaDeUser.contains(user)
    }

    fun readAllUsers() : ArrayList<User>{
        var users = ArrayList<User>()
        var db = readableDatabase
        var queryResult = db.rawQuery("SELECT * FROM USERS",null)
        if(queryResult.moveToNext()){
            while (!queryResult.isAfterLast ){
             //   users.add(User(queryResult.getString(1),queryResult.getString(2)))
             //   queryResult.moveToNext()
            }
        }
        return users
    }

    fun insertUser(user: User){
        var db = writableDatabase
        var contentValues = ContentValues()
        contentValues.put("NAME",user.name)
        contentValues.put("PWD", user.pwd)
        db.insert("USERS",null,contentValues)
    }
}