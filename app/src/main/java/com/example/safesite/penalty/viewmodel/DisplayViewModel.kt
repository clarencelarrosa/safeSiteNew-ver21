package com.example.safesite.penalty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.safesite.penalty.Event
import com.example.safesite.penalty.database.PenaltyRepository

class DisplayViewModel (private val repository: PenaltyRepository) : ViewModel()  {
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    fun getSavedPenalty() = liveData {
        repository.penalty.collect {
            emit(it)
        }
    }

}