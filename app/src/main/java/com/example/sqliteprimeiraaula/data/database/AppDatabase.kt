package com.example.sqliteprimeiraaula.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sqliteprimeiraaula.data.User
import com.example.sqliteprimeiraaula.data.dao.UserDAO

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun UserDAO() : UserDAO
}