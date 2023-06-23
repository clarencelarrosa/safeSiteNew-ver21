package com.example.safesite.todo.database

import androidx.lifecycle.LiveData

class  TodoRepository(private val noteDAO: TodoDAO) {

    val allNotes: LiveData<List<Todo>> = noteDAO.getAllNotes()

    suspend fun insert(note: Todo) = noteDAO.insertNote(note)

    suspend fun delete(note: Todo) = noteDAO.deleteNote(note)

    suspend fun update(note: Todo) = noteDAO.updateNote(note)
}