package com.example.safesite.penalty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.safesite.penalty.database.PenaltyRepository
import java.lang.IllegalArgumentException

class DeleteViewModelFactory (private val repository: PenaltyRepository):
    ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeleteViewModel::class.java)) {
            return DeleteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}