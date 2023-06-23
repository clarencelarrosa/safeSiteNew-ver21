package com.example.safesite.penalty.viewmodel

import androidx.lifecycle.*
import com.example.safesite.penalty.Event
import com.example.safesite.penalty.database.Penalty
import com.example.safesite.penalty.database.PenaltyRepository
import kotlinx.coroutines.launch

class UpdateViewModel  (private val repository: PenaltyRepository) : ViewModel() {

    private lateinit var penaltyToUpdate: Penalty
    val penaltyDate = MutableLiveData<String>()
    val penaltyAmount = MutableLiveData<String>()
    val penaltyDesc = MutableLiveData<String>()

    val UpdateSearchText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage
    init{
        UpdateSearchText.value = "Search"
    }

    fun initUpdate(penalty: Penalty){
        penaltyDate.value = penalty.penaltyDate
        penaltyAmount.value = penalty.penaltyAmount
        penaltyDesc.value = penalty.penaltyDesc

        penaltyToUpdate = penalty
        UpdateSearchText.value = "Update"
    }

    fun search(penaltyDate: String) = liveData {

        repository.search(penaltyDate).collect {
            emit(it)
        }
    }
    fun update(){
        penaltyToUpdate.penaltyDate= penaltyDate.value!!
        penaltyToUpdate.penaltyAmount= penaltyAmount.value!!
        penaltyToUpdate.penaltyDesc= penaltyDesc.value!!

        updatePenalty(penaltyToUpdate)
    }

    private fun updatePenalty(penalty: Penalty) = viewModelScope.launch {
        val noOfRows = repository.update(penalty)
        if (noOfRows > 0) {
            penaltyDate.value = ""
            penaltyAmount.value = ""
            penaltyDesc.value = ""

            statusMessage.value = Event("$noOfRows penalty has been updated successfully")
        }
        else {
            statusMessage.value = Event("An error occurred")
        }
    }
}