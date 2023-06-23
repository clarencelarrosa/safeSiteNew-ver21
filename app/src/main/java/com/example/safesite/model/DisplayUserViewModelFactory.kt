package com.example.safesite.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.safesite.database.RegisterRepository

class DisplayUserViewModelFactory (

    private val repository: RegisterRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DisplayUserViewModel::class.java)){
            return DisplayUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}