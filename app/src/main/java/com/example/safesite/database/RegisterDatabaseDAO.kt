package com.example.safesite.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RegisterDatabaseDAO {

    @Insert
    suspend fun insertRegister(register: Register): Long

    @Query
        ("select * from user_register_table order by userId asc")
    fun displayUsers(): LiveData<List<Register>>
/*
    @Query
        ("delete from user_register_table")
    suspend fun deleteAll(): Int
 */
    @Query
        ("select * from user_register_table where username =:username")
    suspend fun getUsername(username: String): Register?
}