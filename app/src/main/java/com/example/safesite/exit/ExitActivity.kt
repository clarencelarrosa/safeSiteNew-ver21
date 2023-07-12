package com.example.safesite.exit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.safesite.MainActivity
import com.example.safesite.R

class ExitActivity : AppCompatActivity()
 //   , Interface
{
    lateinit var logout: Button
    lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit)

        /*
        //NEW ADD- PROFILE
        val registerFragment = RegistrationFragment()
        textUsername = findViewById(R.id.textUsername)
         */

        val view  = layoutInflater
        //set the title of the action of the activity
        val actionBar = supportActionBar
        actionBar!!.title="Exit"

        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //logout button
        logout = findViewById(R.id.logout)
        logout.setOnClickListener {
            Toast.makeText(this,"User has now logged out", Toast.LENGTH_SHORT).show() //this will appear when logging out
            val intent = Intent(this, MainActivity::class.java) //will redirect to the main activity (login)
            startActivity(intent)
            finish()
        }
    }
    //action for back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}