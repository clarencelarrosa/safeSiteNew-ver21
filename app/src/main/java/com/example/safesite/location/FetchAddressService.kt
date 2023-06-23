package com.example.safesite.location

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import android.widget.Toast
import java.util.*

class FetchAddressService : IntentService("FetchAddressService") {
    var resultReceiver: ResultReceiver? = null
    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            var errormessgae = ""
            resultReceiver = intent.getParcelableExtra(Constants.RECEIVER)
            val location = intent.getParcelableExtra<Location>(Constants.LOCATION_DATA_EXTRA)
                ?: return
            val geocoder = Geocoder(this, Locale.getDefault())
            var addresses: List<Address>? = null
            try {
                addresses = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
            } catch (ioException: Exception) {
                Log.e("", "Error occurred in getting address for the location")
            }
            if (addresses == null || addresses.size == 0) {
                errormessgae = "No location address found"
                Toast.makeText(this, "" + errormessgae, Toast.LENGTH_SHORT).show()
            } else {
                val address = addresses[0]
                val str_Country = address.countryName
                val str_district = address.subAdminArea
                val str_address = address.featureName
                deliverResultToReceiver(
                    Constants.SUCCESS_RESULT,
                    str_address,
                    str_district,
                    str_Country,
                )
            }
        }
    }

    private fun deliverResultToReceiver(
        resultcode: Int,
        address: String,
        district: String,
        country: String,
    ) {
        val bundle = Bundle()
        bundle.putString(Constants.ADDRESS, address)
        bundle.putString(Constants.DISTRICT, district)
        bundle.putString(Constants.COUNTRY, country)
        resultReceiver!!.send(resultcode, bundle)
    }
}

