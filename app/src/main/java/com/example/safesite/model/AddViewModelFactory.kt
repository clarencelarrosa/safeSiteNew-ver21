package com.example.safesite.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.safesite.database.RegisterRepository

class AddViewModelFactory (
    private val repository: RegisterRepository,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}