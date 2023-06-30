package com.example.safesite.record

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordsViewModel(app: Application):AndroidViewModel(app){
    private var allRecords: MutableLiveData<List<Record>> = MutableLiveData()

    init {
        getAllRecords()
    }

    fun getAllRecordsObservers(): MutableLiveData<List<Record>> {
        return allRecords
    }

    private fun getAllRecords() {
        GlobalScope.launch {
            val recordDao= RecordDatabase.getDatabase(getApplication())?.recordDao()
            val recordsList = recordDao?.getAllRecords()
            allRecords.postValue(recordsList!!)
        }
    }

    fun insertRecordInfo(record: Record) {
        GlobalScope.launch {
            val recordDao = RecordDatabase.getDatabase(getApplication())?.recordDao()
            recordDao?.insertRecord(record)
        }
        getAllRecords()
    }

    fun updateRecordInfo(record: Record) {
        GlobalScope.launch {
            val recordDao = RecordDatabase.getDatabase(getApplication())?.recordDao()
            recordDao?.updateRecord(record)
        }
        getAllRecords()
    }

    fun deleteRecordInfo(record: Record) {
            val recordDao = RecordDatabase.getDatabase(getApplication())?.recordDao()
            recordDao?.deleteRecord(record)
        getAllRecords()
    }
}