package com.example.safesite.todo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.safesite.R
import com.example.safesite.databinding.ActivityTodoMainBinding
import com.example.safesite.todo.database.Todo
import com.google.android.material.snackbar.Snackbar

class TodoMainActivity : AppCompatActivity(), INoteRVAdapter {
    private var binding: ActivityTodoMainBinding? = null
    lateinit var viewModel: NoteViewModel
    lateinit var noteAdapter: NoteRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.fabAddTask?.setOnClickListener {
            val addNoteIntent = Intent(this@TodoMainActivity, AddToDo::class.java)
            startActivity(addNoteIntent)
        }

        //initializing recyclerView
        binding?.recyclerView?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        //initializing adapter
        noteAdapter = NoteRVAdapter(this, this)

        //passing adapter to recyclerView
        binding?.recyclerView?.adapter = noteAdapter

        //setting swipe to delete item
        setUpSwipeToDeleteItem()

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer {
            Log.d("MYTAG", "$it")
            noteAdapter.submitList(it)
        })

        val actionBar = supportActionBar
        actionBar!!.title="To Do Tasks"

        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpSwipeToDeleteItem() {
        val swipeToDelete = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition
                val currentList =  noteAdapter.currentList.toMutableList()
                val swipedItem = currentList[itemPosition]
                currentList.removeAt(itemPosition)

                viewModel.removeTask(swipedItem)

                noteAdapter.submitList(currentList)

                val snackbar = Snackbar.make(binding?.root!!, "Task deleted", Snackbar.LENGTH_LONG)
                snackbar.setAction("Undo deleted task") {
                    val newCurrentList  = noteAdapter.currentList.toMutableList()
                    newCurrentList.add(itemPosition, swipedItem)

                    viewModel.addTask(swipedItem)
                    noteAdapter.submitList(newCurrentList)
                }
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(binding?.recyclerView)
    }

    private fun runRecyclerViewAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.recyclerview_animation)
        recyclerView.layoutAnimation = controller
        recyclerView.scheduleLayoutAnimation()
    }

    override fun onResume() {
        super.onResume()
        runRecyclerViewAnimation(binding?.recyclerView!!)
    }


    //action for back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCardClicked(note: Todo) {
        //whenever a card of note is clicked we navigate to openNote activity passing the note
        val openNoteIntent = Intent(this, OpenToDo::class.java)
        openNoteIntent.putExtra("Task selected", note)
        startActivity(openNoteIntent)
    }
}