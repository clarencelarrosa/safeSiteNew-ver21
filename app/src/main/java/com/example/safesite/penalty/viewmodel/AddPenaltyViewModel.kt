package com.example.safesite.penalty.viewmodel

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
/*
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateto = MutableLiveData<Boolean>()
    val navigateto: LiveData<Boolean>
    get() = _navigateto

    private val _errorToast=MutableLiveData<Boolean>()
    val errortoast: LiveData<Boolean>
    get() = _errorToast

    private val _errorToastDate = MutableLiveData<Boolean>()
    val errortoastDate: LiveData<Boolean>
    get() = _errorToastDate

    private val _errorToastAmount = MutableLiveData<Boolean>()
    val errortoastAmount: LiveData<Boolean>
        get() = _errorToastDate


    private val _errorToastDesc = MutableLiveData<Boolean>()
    val errortoastDesc: LiveData<Boolean>
        get() = _errorToastDesc

    fun btnSave() {
        Log.i("MYTAG","INSIDE SAVE BUTTON")
        if (penaltyDate.value!!.isEmpty()) {
            _errorToastDate.value = true
        }
        if (penaltyAmount.value!!.isEmpty()) {
            _errorToastAmount.value = true
        }
        if (penaltyDesc.value!!.isEmpty()) {
            _errorToastDesc.value = true
        }
    }


    fun btnSave() {
        Log.i("MYTAG","INSIDE THE SAVE BUTTON")
        if (penaltyDate.value == null || penaltyAmount.value == null || penaltyDesc.value==null) {
            _errorToast.value=true
        } else {
            uiScope.launch {
                val
            }
        }
    }

     */

    fun save(){
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
            statusMessage.value = Event("Penalty Successfully inserted $newRowId")
        } else {
            statusMessage.value = Event("An error occurred")
        }
    }
}