package com.example.safesite.penalty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.safesite.penalty.database.PenaltyRepository

class UpdateViewModelFactory (
    private val repository: PenaltyRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpdateViewModel::class.java)){
            return UpdateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}