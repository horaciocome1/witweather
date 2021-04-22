package io.github.horaciocome1.witweather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import io.github.horaciocome1.witweather.databinding.ActivityMainBinding
import io.github.horaciocome1.witweather.ui.home.HomeSharedViewModel
import io.github.horaciocome1.witweather.util.Constants


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val sharedViewModel: HomeSharedViewModel by viewModels()

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val locationRequest: LocationRequest by lazy {
        val request = LocationRequest.create()
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        request.interval = 2 * 60 * 1000
        return@lazy request
    }

    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    sharedViewModel.setGeoCoordinates(location.latitude, location.longitude)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        managePermissionRequests()
        if (isPermissionGranted()) {
            monitorLocationChanges()
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.REQUEST_CODE_LOCATION_PERMISSIONS -> {
                val isGranted = grantResults.first() == PackageManager.PERMISSION_GRANTED
                if (isGranted) {
                    sharedViewModel.setLocationPermissionGranted(true)
                    return
                }
                sharedViewModel.setLocationPermissionGranted(false)
            }
        }
    }

    private fun managePermissionRequests() {
        sharedViewModel.isLocationPermissionRequested.observe(this) {
            if (it) {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Constants.REQUEST_CODE_LOCATION_PERMISSIONS
                )
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        val isFineLocationGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        val isCoarseLocationGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        val isPermissionGranted = isFineLocationGranted && isCoarseLocationGranted
        sharedViewModel.setLocationPermissionGranted(isPermissionGranted)
        return isPermissionGranted
    }

    @SuppressLint("MissingPermission")
    private fun monitorLocationChanges() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

}