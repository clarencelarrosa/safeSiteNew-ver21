package com.example.safesite.attendance

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.safesite.R
import com.example.safesite.databinding.ActivityEditDateBinding

import com.example.safesite.databinding.AttendanceDialogBinding
import com.example.safesite.databinding.ConfirmDeleteDateDialogBinding

import com.example.safesite.databinding.DateDialogBinding


class EditDateActivity : AppCompatActivity() {

    private lateinit var dateList: DateAttend
    private lateinit var binding: ActivityEditDateBinding
    private var index: Int? = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //set the title of the action of the activity
        val actionBar = supportActionBar
        actionBar!!.title="Edit Attendance"

        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //shared preference
        index = intent.getIntExtra("attendanceListIndex", 0)
        getDateList()
        binding.attendanceListEditName.text = dateList.name

        val adapter =
            AttendanceAdapter(this, dateList.products.keys.toTypedArray(), dateList.products)
        binding.attendanceList.setAdapter(adapter)
        binding.attendanceList.expandGroup(0)
        binding.attendanceList.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            showEditProductDialog(groupPosition,
                childPosition)
            true
        }
        binding.attendanceList.setOnGroupClickListener { _, _, i, _ ->
            binding.attendanceList.expandGroup(i)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.edit_attendance_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_date_list -> {
                showConfirmDeleteDateList()
                true
            }
            R.id.edit_date_list -> {
                showEditDateListNameDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showConfirmDeleteDateList() {
        val dialog = Dialog(this)
        dialog.setTitle("Delete date list")
        val dialogBinding = ConfirmDeleteDateDialogBinding.inflate(layoutInflater)
        dialogBinding.confirmButton.setOnClickListener {
            val tinyDB = SharedPreferencesService(applicationContext)
            val list = tinyDB.getDateLists("attendance_list", DateAttend::class.java)
            list.removeAt(index!!)
            tinyDB.saveDateLists("attendance_list", list)
            dialog.dismiss()
            finish()
        }
        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    private fun getDateList() {
        val tinyDB = SharedPreferencesService(applicationContext)
        val list = tinyDB.getDateLists("attendance_list", DateAttend::class.java)
        dateList = list[index!!] as DateAttend
    }

    private fun saveDateList() {
        val tinyDB = SharedPreferencesService(applicationContext)
        val list = tinyDB.getDateLists("attendance_list", DateAttend::class.java)
        list[index!!] = dateList
        tinyDB.saveDateLists("attendance_list", list)
        binding.attendanceList.setAdapter(
            AttendanceAdapter(
                this,
                dateList.products.keys.toTypedArray(),
                dateList.products
            )
        )
    }

    private fun showEditDateListNameDialog() {
        val dialog = Dialog(this)
        dialog.setTitle("Edit date list name")
        dialog.setContentView(R.layout.date_dialog)
        val dialogBinding = DateDialogBinding.inflate(layoutInflater)
        dialogBinding.createDateListBtn.text = getString(R.string.update)
        dialogBinding.dateListInputText.setText(dateList.name)
        dialogBinding.createDateListBtn.setOnClickListener {
            dateList.name = dialogBinding.dateListInputText.text.toString()
            binding.attendanceListEditName.text = dateList.name
            saveDateList()
            dialog.dismiss()
            Toast.makeText(this, "Date updated", Toast.LENGTH_SHORT).show()
        }
        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    fun showAddAttendanceDialog(@Suppress("UNUSED_PARAMETER") view: View) {
        val dialog = Dialog(this)
        dialog.setTitle("New attendance")
        dialog.setContentView(R.layout.attendance_dialog)
        val dialogBinding = AttendanceDialogBinding.inflate(layoutInflater)
        dialogBinding.deleteAttendanceBtn.visibility = View.GONE
        dialogBinding.createAttendanceBtn.setOnClickListener {
           val quantity = dialogBinding.attendanceQuantityInputText.text.toString()
            val category = dialogBinding.categoryInputText.text.toString()
            val checkedCategory = category.ifEmpty { "No Site" }

            val quantityInt = try {
                quantity.toInt()
            } catch (e: NumberFormatException) {
                0
            }
            val productList = dateList.products[checkedCategory]
            if (productList.isNullOrEmpty()) {
                dateList.products[checkedCategory] = arrayListOf(
                    Attendance(
                        dialogBinding.attendanceNameInputText.text.toString(),
                        quantityInt,
                        dialogBinding.attendanceUnitInputText.text.toString()
                    )
                )
            } else {
                productList.add(
                    Attendance(
                        dialogBinding.attendanceNameInputText.text.toString(),
                        quantityInt,
                        dialogBinding.attendanceUnitInputText.text.toString()
                    )
                )
            }

            saveDateList()
            dialog.dismiss()
            Toast.makeText(
                this,
                dialogBinding.attendanceNameInputText.text.toString() + " added to " + dateList.name,
                Toast.LENGTH_SHORT
            ).show()
        }
        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    private fun showEditProductDialog(groupPosition: Int, childPosition: Int) {
        val dialog = Dialog(this)
        dialog.setTitle("Edit attendance")
        dialog.setContentView(R.layout.attendance_dialog)
        val dialogBinding = AttendanceDialogBinding.inflate(layoutInflater)
        dialogBinding.createAttendanceBtn.text = getString(R.string.update)
        dialogBinding.attendanceNameInputText.setText(
            dateList.products[dateList.products.keys.toTypedArray()[groupPosition]]?.get(
                childPosition
            )?.name
        )
        dialogBinding.attendanceQuantityInputText.setText(
            dateList.products[dateList.products.keys.toTypedArray()[groupPosition]]?.get(
                childPosition
            )?.time.toString()
        )
        dialogBinding.attendanceUnitInputText.setText(
            dateList.products[dateList.products.keys.toTypedArray()[groupPosition]]?.get(
                childPosition
            )?.unit
        )
        dialogBinding.categoryInputText.setText(
            dateList.products.keys.toTypedArray()[groupPosition]
        )
        dialogBinding.createAttendanceBtn.setOnClickListener {
            val newCategory = dialogBinding.categoryInputText.text.toString()
            val checkedNewCategory = newCategory.ifEmpty { "No Site name" }
            val oldCategory = dateList.products.keys.toTypedArray()[groupPosition]
            val product = Attendance(
                dialogBinding.attendanceNameInputText.text.toString(),
                0,
                dialogBinding.attendanceUnitInputText.text.toString()
            )
            val quantity = dialogBinding.attendanceQuantityInputText.text.toString()
            try {
                product.time = quantity.toInt()
            } catch (e: NumberFormatException) {
                product.time = 0
            }
            if (checkedNewCategory == oldCategory) {
                dateList.products[dateList.products.keys.toTypedArray()[groupPosition]]?.set(
                    childPosition,
                    product
                )
            } else {
                dateList.products[dateList.products.keys.toTypedArray()[groupPosition]]?.removeAt(
                    childPosition
                )
                if (dateList.products[dateList.products.keys.toTypedArray()[groupPosition]]?.isEmpty() == true) {
                    dateList.products.remove(dateList.products.keys.toTypedArray()[groupPosition])
                }
                val products = dateList.products[checkedNewCategory]
                if (products.isNullOrEmpty()) {
                    dateList.products[checkedNewCategory] = arrayListOf(product)
                } else {
                    products.add(product)
                }
            }
            saveDateList()
            dialog.dismiss()
            Toast.makeText(
                this,
                dialogBinding.attendanceNameInputText.text.toString() + " updated",
                Toast.LENGTH_SHORT
            ).show()
        }
        dialogBinding.deleteAttendanceBtn.setOnClickListener {
            dateList.products[dateList.products.keys.toTypedArray()[groupPosition]]?.removeAt(
                childPosition
            )
            saveDateList()
            dialog.dismiss()
            Toast.makeText(
                this,
                dialogBinding.attendanceNameInputText.text.toString() + " deleted from " + dateList.name,
                Toast.LENGTH_SHORT
            ).show()
        }
        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

    fun saveCheckedProducts(@Suppress("UNUSED_PARAMETER") view: View) {
        saveDateList()
        Toast.makeText(
            this,
            "The checked PPE/s has been saved",
            Toast.LENGTH_SHORT
        ).show()
    }

    //action for back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}