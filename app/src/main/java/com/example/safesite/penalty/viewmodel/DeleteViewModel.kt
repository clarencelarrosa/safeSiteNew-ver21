package com.example.safesite.penalty.viewmodel

import androidx.lifecycle.*
import com.example.safesite.penalty.Event
import com.example.safesite.penalty.database.Penalty
import com.example.safesite.penalty.database.PenaltyRepository
import kotlinx.coroutines.launch

class DeleteViewModel (private val repository: PenaltyRepository): ViewModel() {

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = statusMessage

    fun getSavedPenalty() = liveData {
        repository.penalty.collect {
            emit(it)
        }
    }

    fun deletePenalty (penalty: Penalty) = viewModelScope.launch {
        repository.delete(penalty)
        statusMessage.value = Event("${penalty.penaltyDate} has been deleted")
    }
}