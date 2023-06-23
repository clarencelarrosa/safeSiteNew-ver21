package com.example.safesite.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.safesite.databinding.ActivityOpenNoteBinding
import com.example.safesite.todo.database.Todo

class OpenToDo : AppCompatActivity() {
    private var binding: ActivityOpenNoteBinding? = null
    private var selectedNote: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        selectedNote = intent.getSerializableExtra("Task selected") as Todo

        binding?.llBg?.setBackgroundColor(selectedNote!!.color!!)
        binding?.tvTitleOpen?.text = selectedNote?.title
        binding?.tvDesOpen?.text = selectedNote?.description

       /*
        //back button functionality
        binding?.ivBackButton?.setOnClickListener {
            finish()
        }
        */
        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val actionBar = supportActionBar
        actionBar!!.title="Open Note"


        binding?.ivEditButton?.setOnClickListener {
            val editNoteIntent = Intent(this, EditToDo::class.java)
            editNoteIntent.putExtra("Update selected task", selectedNote)
            startActivity(editNoteIntent)
        }

        binding?.tvTitleOpen?.text = selectedNote?.title
        binding?.tvDesOpen?.text = selectedNote?.description
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