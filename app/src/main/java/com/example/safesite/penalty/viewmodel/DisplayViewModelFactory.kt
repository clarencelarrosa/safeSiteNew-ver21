package com.example.safesite.penalty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.safesite.penalty.database.PenaltyRepository

class DisplayViewModelFactory  (

    private val repository: PenaltyRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DisplayViewModel::class.java)){
            return DisplayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}