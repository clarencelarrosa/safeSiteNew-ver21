package com.example.safesite.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.safesite.R
import com.example.safesite.databinding.ActivityAddNoteBinding
import com.example.safesite.databinding.ActivityTodoMainBinding
import com.example.safesite.todo.database.Todo
import com.google.android.material.snackbar.Snackbar

class AddToDo : AppCompatActivity() {
    private var binding: ActivityAddNoteBinding? = null
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

/*
        //back button functionality
        binding?.ivBackButton?.setOnClickListener {
            finish()
        }
 */

        //Set title
        val actionBar = supportActionBar
        actionBar!!.title="Add Task"

        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        binding?.btnAdd?.setOnClickListener {
            if (binding?.etTaskTitle?.text.toString().isNotEmpty() && binding?.etTaskDescription?.text.toString().isNotEmpty()) {

                val colorsArray = resources.getIntArray(R.array.cardColors)
                val randomInt = (0..9).random()
                Log.d("MYTAG", "ANY COLOR $randomInt")
                val randomColor =  colorsArray[randomInt]
                Log.d("MYTAG", "ANY COLOR $randomColor")
                viewModel.addTask(Todo(0, binding?.etTaskTitle?.text.toString(), binding?.etTaskDescription?.text.toString(), randomColor))
                val mainIntent = Intent(this, ActivityTodoMainBinding::class.java)
                startActivity(mainIntent)
            }else {
                Snackbar.make(binding?.root!!, "Enter title of the task with description", Snackbar.LENGTH_SHORT).show()
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