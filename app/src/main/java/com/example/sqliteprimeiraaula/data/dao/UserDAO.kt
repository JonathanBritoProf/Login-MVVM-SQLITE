package com.example.sqliteprimeiraaula.data.dao

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.Query
import com.example.sqliteprimeiraaula.data.User

@Dao
interface UserDAO {

    @Query("SELECT * FROM user WHERE name LIKE :name AND " +
            "pwd LIKE :password LIMIT 1")
    fun findByName(name: String, password: String): User?

    @Insert
    fun insert(user: User)

}