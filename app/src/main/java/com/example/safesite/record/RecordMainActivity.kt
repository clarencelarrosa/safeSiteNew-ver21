package com.example.safesite.record

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.safesite.R
import com.example.safesite.databinding.ActivityRecordMainBinding
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordMainActivity : AppCompatActivity() {

    private lateinit var viewModel: RecordsViewModel
    private lateinit var recordList: List<Record>
    private lateinit var initialRecordList: List<Record>
    private lateinit var deletedRecord: Record
    private lateinit var recordAdapter: RecordAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recordDB: RecordDatabase

    private lateinit var binding: ActivityRecordMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  setContentView(R.layout.activity_record_main) */
        binding = ActivityRecordMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Add Attendance"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recordList = arrayListOf()
        recordAdapter = RecordAdapter(recordList)
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout=true
        linearLayoutManager.stackFromEnd = true

        recordDB = Room.databaseBuilder(
            this,
            RecordDatabase::class.java,
        "records"
        ).build()


        binding.listRecords.apply {
            adapter = recordAdapter
            layoutManager = linearLayoutManager
        }

        viewModel = ViewModelProvider(this).get(RecordsViewModel::class.java)

        binding.fab.setOnClickListener {
            val intent = Intent(this, NewRecordActivity::class.java)
            startActivity(intent)
        }

        swipeToDelete()
        showEmptyMessage()
    }
    private fun fetchData() {
        GlobalScope.launch {
            recordList = recordDB.recordDao().getAllRecords()

            runOnUiThread {
                recordAdapter.setListData(recordList)
            }
        }
    }

    private fun deleteRecord(record: Record) {
        deletedRecord = record
        initialRecordList = recordList

        GlobalScope.launch {
            viewModel.deleteRecordInfo(record)
            recordList = recordList.filter { it.id != record.id }

            runOnUiThread {
                recordAdapter.setListData(recordList)
                showSnackBar()
            }
        }
    }

    private fun swipeToDelete() {
        val itemTouchHelper = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteRecord(recordList[viewHolder.adapterPosition])
            }
        }

        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(binding.listRecords)
    }

    private fun undoDelete() {
        viewModel.insertRecordInfo(deletedRecord)
        recordList = initialRecordList

        runOnUiThread {
            recordAdapter.setListData(recordList)
        }
    }

    private fun showSnackBar() {
        val view = findViewById<View>(R.id.coordinatorLayout)
        val snackBar = Snackbar.make(
            view,
            "Record deleted",
            Snackbar.LENGTH_LONG
        )

        snackBar.setAction("Undo deleted") {
            undoDelete()
        }
            .setActionTextColor(ContextCompat.getColor(this, R.color.black))
            .show()
    }

    private fun showEmptyMessage() {
        recordAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                if (recordAdapter.itemCount==0) {
                    binding.listRecords.visibility= View.GONE
                    binding.tvNote.visibility = View.VISIBLE
                } else {
                    binding.listRecords.visibility = View.VISIBLE
                    binding.tvNote.visibility = View.GONE
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

/*
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
 */
}