package com.example.safesite.penalty.viewmodel

import android.app.Application
import android.database.Observable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safesite.penalty.Event
import com.example.safesite.penalty.database.Penalty
import com.example.safesite.penalty.database.PenaltyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class AddPenaltyViewModel (private val repository: PenaltyRepository) : ViewModel(){


    val penaltyDate = MutableLiveData<String>()
    val penaltyAmount = MutableLiveData<String>()
    val penaltyDesc = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init{
        penaltyDate.value = ""
        penaltyAmount.value = ""
        penaltyDesc.value = ""
    }


    fun save(){ //saving entered penalty

        val penaltyDateval = penaltyDate.value!!
        val penaltyAmountval = penaltyAmount.value!!
        val penaltyDescval = penaltyDesc.value!!

        //save in penalty table in penalty entity
        savePenalty(Penalty(0, penaltyDateval,penaltyAmountval,penaltyDescval))
        penaltyDate.value = ""
        penaltyAmount.value = ""
        penaltyDesc.value = ""
    }

    fun savePenalty(penalty: Penalty) = viewModelScope.launch {
        val newRowId = repository.insert(penalty)
        if (newRowId > -1) {
            statusMessage.value = Event("Penalty Successfully added $newRowId")
        } else {
            statusMessage.value = Event("An error occurred upon saving")
        }
    }
}