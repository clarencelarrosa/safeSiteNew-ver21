package com.example.safesite.database

import android.util.Log


class RegisterRepository (private val dao: RegisterDatabaseDAO){
    suspend fun insert(register: Register): Long {
        return dao.insertRegister(register)
    }
    val users=dao.displayUsers()

    suspend fun getUserName(username: String):Register? {
        Log.i("MYTAG","Inside repository get users fun")
        return dao.getUsername(username)
    }
}