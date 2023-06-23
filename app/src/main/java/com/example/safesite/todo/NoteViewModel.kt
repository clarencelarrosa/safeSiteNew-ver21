package com.example.safesite.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.safesite.todo.database.Todo
import com.example.safesite.todo.database.TodoDatabase
import com.example.safesite.todo.database.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Todo>>
    private val repository: TodoRepository

    init {
        val dao = TodoDatabase.getDatabase(application).getNoteDao()
        repository = TodoRepository(dao)
        allNotes = repository.allNotes
    }

    fun addTask(note: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun removeTask(note: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun updateTask(note: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }
}