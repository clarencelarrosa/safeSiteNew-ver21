package com.example.safesite.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.safesite.MainActivity
import com.example.safesite.R
import com.example.safesite.RegistrationFragment

class ProfileActivity : AppCompatActivity()
 //   , Interface
{
    lateinit var logout: Button
    lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        /*
        //NEW ADD- PROFILE
        val registerFragment = RegistrationFragment()
        textUsername = findViewById(R.id.textUsername)
         */

        val view  = layoutInflater
        //set the title of the action of the activity
        val actionBar = supportActionBar
        actionBar!!.title="Profile"

        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //logout button
        logout = findViewById(R.id.logout)
        logout.setOnClickListener {
            Toast.makeText(this,"User Logout Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
/*
        //NEW ADD - PROFILE
        val intent = intent
        val stringTxt = intent.getStringExtra("xx")
        textUsername.text=stringTxt
 */
    }
    //action for back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}