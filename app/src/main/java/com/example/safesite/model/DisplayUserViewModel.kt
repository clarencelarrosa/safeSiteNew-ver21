package com.example.safesite.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.safesite.database.RegisterRepository

class DisplayUserViewModel (private val repository: RegisterRepository) : ViewModel()  {

    //NEW
    val users=repository.users
    init {
        Log.i("MYTAG", "Inside users list init")
    }

    private val _navigateto = MutableLiveData<Boolean>()
    val navigateto: LiveData<Boolean>
        get() = _navigateto

    fun doneNavigating(){
        _navigateto.value=false
    }

}