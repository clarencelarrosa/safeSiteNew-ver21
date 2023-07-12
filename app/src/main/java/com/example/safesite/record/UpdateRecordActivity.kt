package com.example.safesite.record

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.safesite.databinding.ActivityUpdateRecordBinding
import kotlin.math.abs
import kotlin.math.log10

class UpdateRecordActivity : AppCompatActivity() {

    private lateinit var record: Record
    private lateinit var viewModel: RecordsViewModel
    private lateinit var binding: ActivityUpdateRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* setContentView(R.layout.activity_update_record)*/
        binding = ActivityUpdateRecordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        record = intent.getSerializableExtra("EMPLOYEE_DATA") as Record
        viewModel = ViewModelProvider(this).get(RecordsViewModel::class.java)

        supportActionBar?.title = "Update Record"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.etUpName.setText(record.name)
        binding.etUpSiteName.setText(record.siteName)
        binding.etUpPPE.setText(record.ppe)
        binding.etUpDate.setText(record.date)
        binding.etUpTotal.setText(record.total.toString())

        binding.etUpName.addTextChangedListener {
            if (it!!.count() > 0) {
                binding.tilUpName.error = null
            }
            binding.btnUpdateRecord.visibility= View.VISIBLE
        }

        binding.etUpSiteName.addTextChangedListener {
            if (it!!.count() > 0) {
                binding.tilUpSiteName.error = null
            }
            binding.btnUpdateRecord.visibility= View.VISIBLE
        }

        binding.etUpPPE.addTextChangedListener {
            if (it!!.count() > 0) {
                binding.tilUpPPE.error = null
            }
            binding.btnUpdateRecord.visibility= View.VISIBLE
        }

        binding.etUpDate.addTextChangedListener {
            if (it!!.count() > 0) {
                binding.tilUpDate.error = null
            }
            binding.btnUpdateRecord.visibility= View.VISIBLE
        }

        binding.etUpTotal.addTextChangedListener {
            if (it!!.count() > 0) {
                binding.tilUpTotal.error = null
            }
            binding.btnUpdateRecord.visibility= View.VISIBLE
        }

        updateRecordInfo()
    }

    private fun updateRecordInfo() {
        binding.btnUpdateRecord.setOnClickListener {
            val name: String = binding.etUpName.text.toString()
            val siteName: String = binding.etUpSiteName.text.toString()
            val ppe: String = binding.etUpPPE.text.toString()
            val date: String = binding.etUpDate.text.toString()
            val total: Int? = binding.etUpTotal.text.toString().toIntOrNull()

            when {
                name.isEmpty() -> {
                    binding.tilUpName.error = "Please enter your name"
                }
                siteName.isEmpty() -> {
                    binding.tilUpSiteName.error = "Please enter the site location"
                }
                ppe.isEmpty() -> {
                    binding.tilUpPPE.error = "Please enter the PPEs worn"
                }
                date.isEmpty() -> {
                    binding.tilUpDate.error = "Please enter date today"
                }
                total    == null -> {
                    binding.tilUpTotal.error = "Total PPE worn today"
                }
                total < 0 || total > 5 -> {
                    binding.tilUpTotal.error = "Please enter valid total PPE"
                }
                else -> {
                    val record = Record(record.id,name, siteName, ppe,date,total)
                    viewModel.updateRecordInfo(record)


                    val appContext = applicationContext

                    makeStatusNotification("Record successfully updated ", appContext)
                    sleep()

                    finish()
                }
            }
        }
    }

    /*
    private fun Int.length() = when(this) {
        0 -> 1
        else -> log10(abs(toDouble())).toInt() + 1
    }
     */

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}