package com.example.safesite.attendance

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.safesite.databinding.ActivityAttendanceMainBinding
import com.example.safesite.databinding.DateDialogBinding

class AttendanceMainActivity : AppCompatActivity() {

    private var listOfDateItem = ArrayList<Any>()
    private lateinit var binding: ActivityAttendanceMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //set the title of the action of the activity
        val actionBar = supportActionBar
        actionBar!!.title="Attendance"

        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = DateAdapter(this, listOfDateItem)
        binding.dateItemList.adapter = adapter
        binding.dateItemList.setOnItemClickListener { _, _, position, _ ->
            showEditDateListActivity(position)
        }
        if (listOfDateItem.isNotEmpty()) {
            binding.noDateList.visibility = View.GONE
        } else {
            binding.noDateList.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        val tinyDB = SharedPreferencesService(applicationContext)
        listOfDateItem = tinyDB.getDateLists("attendance_list", DateAttend::class.java)
        binding.dateItemList.adapter = DateAdapter(this, listOfDateItem)
        if (listOfDateItem.isNotEmpty()) {
            binding.noDateList.visibility = View.GONE
        } else {
            binding.noDateList.visibility = View.VISIBLE
        }
    }

    private fun saveDateList(dateItems: ArrayList<Any>) {
        val tinyDB = SharedPreferencesService(applicationContext)
        tinyDB.saveDateLists("attendance_list", dateItems)
        listOfDateItem = tinyDB.getDateLists("attendance_list", DateAttend::class.java)
        binding.dateItemList.adapter = DateAdapter(this, listOfDateItem)
        if (listOfDateItem.isNotEmpty()) {
            binding.noDateList.visibility = View.GONE
        } else {
            binding.noDateList.visibility = View.VISIBLE
        }
    }

    private fun showEditDateListActivity(position: Int) {
        val intent = Intent(this, EditDateActivity::class.java)
        intent.putExtra("attendanceListIndex", position)
        startActivityForResult(intent, position)
    }

    fun showCreateDateDialog(@Suppress("UNUSED_PARAMETER") view: View) {
        val dialog = Dialog(this)
        dialog.setTitle("New attendance list")
        val dialogBinding = DateDialogBinding.inflate(layoutInflater)
        dialogBinding.createDateListBtn.setOnClickListener {
            listOfDateItem.add(
                DateAttend(
                    dialogBinding.dateListInputText.text.toString(),
                    hashMapOf("No Site" to arrayListOf())
                )
            )
            saveDateList(listOfDateItem)
            dialog.dismiss()
            Toast.makeText(
                this,
                dialogBinding.dateListInputText.text.toString() + " created",
                Toast.LENGTH_SHORT
            ).show()
        }
        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    //action for back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

