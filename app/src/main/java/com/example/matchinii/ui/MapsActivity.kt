package com.example.matchinii.ui

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.matchinii.R
import com.example.matchinii.databinding.ActivityMapsBinding
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsActivity: AppCompatActivity(), OnMapReadyCallback {

    // 2.
    private var mLocationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL = (10 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    private var REQUEST_LOCATION_CODE = 101
    private var latitude = 0.0
    private var longitude = 0.0

    private lateinit var mGoogleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }

   /* private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
                    })
                    .create()
                    .show()

            } else ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_CODE)
        }
    }*/
    override fun onMapReady(googleMap: GoogleMap) {

        mGoogleMap = googleMap;

        if (mGoogleMap != null) {
            mGoogleMap!!.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title("Current Location"))


        }
        else {
            //Request Location Permission
//checkLocationPermission()

        }

    }

    // 3.
    protected fun startLocationUpdates() {
        // initialize location request object
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.run {
            setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            setInterval(UPDATE_INTERVAL)
            setFastestInterval(FASTEST_INTERVAL)
        }

        // initialize location setting request builder object
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest = builder.build()

        // initialize location service object
        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient!!.checkLocationSettings(locationSettingsRequest)

        // call register location listener
        registerLocationListner()
    }

    private fun registerLocationListner() {
        // initialize location callback object
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                onLocationChanged(locationResult!!.getLastLocation())
            }
        }
        // 4. add permission if android version is greater then 23
        if(Build.VERSION.SDK_INT >= 23 && checkPermission()) {
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper())
        }
    }

    private fun onLocationChanged(location: Location) {
        // create message for toast with updated latitude and longitudefa
        var msg = "Updated Location: " + location.latitude  + " , " +location.longitude

        // show toast message with updated location
        //Toast.makeText(this,msg, Toast.LENGTH_LONG).show()
        val location = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions().position(location).title("I Am Here!")
        Log.e("hhh",location.latitude.toString())
        Log.e("hhh",location.longitude.toString())
        mGoogleMap!!.clear()
        mGoogleMap.addMarker(markerOptions)
       // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
       mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
       mGoogleMap.addMarker(markerOptions)
    }

    private fun checkPermission() : Boolean {
        if (ContextCompat.checkSelfPermission(this , android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions()
            return false
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf("Manifest.permission.ACCESS_FINE_LOCATION"),1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1) {
            if (permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION ) {
                registerLocationListner()
            }
        }
    }
}