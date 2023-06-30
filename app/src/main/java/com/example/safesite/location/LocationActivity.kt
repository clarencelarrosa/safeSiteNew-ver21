package com.example.safesite.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.safesite.R
import com.google.android.gms.location.*
import java.util.*

class LocationActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null
    var textLatLong: TextView? = null
    var address: TextView? = null
    var district: TextView? = null
    var country: TextView? = null
    var resultReceiver: ResultReceiver? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        resultReceiver = AddressResultReceiver(Handler())
        progressBar = findViewById(R.id.progress_circular)
        textLatLong = findViewById(R.id.textLatLong)
        address = findViewById(R.id.textaddress)
        country = findViewById(R.id.textcountry)
        district = findViewById(R.id.textdistrict)

        //set the title of the action of the activity
        val actionBar = supportActionBar
        actionBar!!.title="Location"

        //back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        findViewById<View>(R.id.btn).setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@LocationActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                currentLocation
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                currentLocation
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val currentLocation: Unit
        private get() {
            progressBar!!.visibility = View.VISIBLE

            val locationRequest = LocationRequest()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 3000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            LocationServices.getFusedLocationProviderClient(this@LocationActivity)
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)

                        LocationServices.getFusedLocationProviderClient(applicationContext)
                            .removeLocationUpdates(this)

                        if (locationResult != null && locationResult.locations.size > 0) {
                            val latestlocIndex = locationResult.locations.size - 1
                            val lati = locationResult.locations[latestlocIndex].latitude
                            val longi = locationResult.locations[latestlocIndex].longitude
                            textLatLong!!.text =
                                String.format("Latitude : %s\n Longitude: %s", lati, longi)
                            val location = Location("providerNA")
                            location.longitude = longi
                            location.latitude = lati
                            fetchaddressfromlocation(location)
                        } else {
                            progressBar!!.visibility = View.GONE
                        }
                    }
                }, Looper.getMainLooper())
        }

    private inner class AddressResultReceiver(handler: Handler?) :
        ResultReceiver(handler) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == Constants.SUCCESS_RESULT) {
                address!!.text = resultData.getString(Constants.ADDRESS)
                district!!.text = resultData.getString(Constants.DISTRICT)
                country!!.text = resultData.getString(Constants.COUNTRY)
            } else {
                Toast.makeText(
                    this@LocationActivity,
                    resultData.getString(Constants.RESULT_DATA_KEY),
                    Toast.LENGTH_SHORT
                ).show()
            }
            progressBar!!.visibility = View.GONE
        }
    }

    private fun fetchaddressfromlocation(location: Location) {
        val intent = Intent(this, FetchAddressService::class.java)
        intent.putExtra(Constants.RECEIVER, resultReceiver)
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location)
        startService(intent)
    }

    //function for back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}

