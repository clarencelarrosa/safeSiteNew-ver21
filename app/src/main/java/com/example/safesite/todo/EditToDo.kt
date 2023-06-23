package com.example.safesite.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.example.safesite.databinding.ActivityEditNoteBinding
import com.example.safesite.databinding.ActivityTodoMainBinding
import com.example.safesite.todo.database.Todo

class EditToDo : AppCompatActivity() {
    private var binding: ActivityEditNoteBinding? = null
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val selectedTask: Todo = intent.getSerializableExtra("Update selected task") as Todo

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val actionBar = supportActionBar
        actionBar!!.title="Edit Task"

        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        binding?.apply {
            etEditTaskTitle.setText(selectedTask.title ?: "")
            etEditTaskDescription.setText(selectedTask.description ?: "")
            llEditBg.setBackgroundColor(selectedTask.color!!)
        }


        binding?.apply {
            btnSave.setOnClickListener {
                val editedTask = Todo(
                    selectedTask.id,
                    etEditTaskTitle.text.toString().takeIf { it.isNotBlank() },
                    etEditTaskDescription.text.toString().takeIf { it.isNotBlank() },
                    selectedTask.color
                )
                viewModel.updateTask(editedTask)
                Toast.makeText(this@EditToDo, "Updated to do task", Toast.LENGTH_SHORT).show()

                val mainIntent = Intent(this@EditToDo, ActivityTodoMainBinding::class.java)
                startActivity(mainIntent)
            }
        }
    }


    //action for back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}