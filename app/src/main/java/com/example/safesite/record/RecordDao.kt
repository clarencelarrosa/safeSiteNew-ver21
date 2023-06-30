package com.example.safesite.record

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface RecordDao {
    @Query("SELECT*FROM records")
    fun getAllRecords():List<Record>
    @Insert
    fun insertRecord(vararg record: Record)
    @Update
    fun updateRecord(vararg record: Record)
    @Delete
    fun deleteRecord(record: Record)
}