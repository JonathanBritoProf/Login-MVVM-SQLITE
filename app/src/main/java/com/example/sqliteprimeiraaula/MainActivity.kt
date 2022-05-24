package com.example.sqliteprimeiraaula

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqliteprimeiraaula.data.helper.DBHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var helper = DBHelper(this)
        var db = helper.readableDatabase
        var queryResult = db.rawQuery("SELECT * FROM USERS WHERE NAME = 'Gustavo'",null)
        if(queryResult.moveToNext()) {
            Toast.makeText(this,queryResult.getString(2),Toast.LENGTH_SHORT).show()
        }


    }
}