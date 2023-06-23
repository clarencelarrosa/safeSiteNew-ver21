package com.example.safesite.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(todo: Todo)

    @Delete
    suspend fun deleteNote(todo: Todo)

    @Query("SELECT * FROM todo_table")
    fun getAllNotes(): LiveData<List<Todo>>

    @Update
    suspend fun updateNote(note: Todo)
}