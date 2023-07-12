package com.example.safesite.record

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.safesite.attendance.DateAttend
import com.example.safesite.databinding.ActivityNewRecordBinding
import java.text.SimpleDateFormat
import java.util.*

import kotlin.math.abs
import kotlin.math.log10
import android.os.Build.VERSION_CODES as BuildVERSION_CODES


class NewRecordActivity : AppCompatActivity() {

    private lateinit var viewModel: RecordsViewModel
    private lateinit var binding: ActivityNewRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewRecordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(RecordsViewModel::class.java)

        supportActionBar?.title = "New Record"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        binding.etName.addTextChangedListener {
            if (it!!.count() > 0 ) {
                binding.tilName.error=null
            }
        }

        binding.etSiteName.addTextChangedListener {
            if (it!!.count() > 0 ) {
                binding.tilSiteName.error = null
            }
        }

        binding.etPPE.addTextChangedListener {
            if (it!!.count() > 0 ) {
                binding.tilPPE.error = null
            }
        }

        binding.etDate.addTextChangedListener {
            if (it!!.count() > 0 ) {
                binding.etDate.error = null
            }
        }

        binding.etTotal.addTextChangedListener {
            if (it!!.count() > 0 ) {
                binding.etTotal.error = null
            }
        }

        addNewRecord()
    }

    private fun addNewRecord() {
        binding.btnAddRecord.setOnClickListener {
            val name: String = binding.etName.text.toString()
            val siteName: String = binding.etSiteName.text.toString()
            val ppe: String = binding.etPPE.text.toString()
            val date: String = binding.etDate.text.toString()
            val total: Int? = binding.etTotal.text.toString().toIntOrNull()


            when {
                name.isEmpty() -> {
                    binding.tilName.error = "Please enter your name"
                }
                siteName.isEmpty() -> {
                    binding.tilSiteName.error = "Please enter the site location"
                }
                ppe.isEmpty() -> {
                    binding.tilPPE.error = "Please enter Vest/Gloves/Helmet/Glasses/Boots"
                }
                date.isEmpty() -> {
                    binding.tilDate.error = "Please enter date today"
                }
                total == null -> {
                    binding.tilTotal.error = "Please enter total PPE (1 to 5)"
                }
                total < 0 || total > 5 -> {
                    binding.tilTotal.error = "Please enter valid total PPE"
                }
                else -> {
                    val record = Record(0,name, siteName, ppe, date, total)
                    viewModel.insertRecordInfo(record)

                    val appContext = applicationContext

                    makeStatusNotification("Record successfully added ", appContext)
                    sleep()

                    finish()
                }
            }
        }
    }

   /*
    private fun Int.length() = when (this) {
        0 -> 1
        else -> log10(abs(toDouble())).toInt() + 1
    }
    */

    //back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}