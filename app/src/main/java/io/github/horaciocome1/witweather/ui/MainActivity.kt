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
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import io.github.horaciocome1.witweather.R
import io.github.horaciocome1.witweather.databinding.ActivityMainBinding
import io.github.horaciocome1.witweather.ui.home.HomeSharedViewModel
import io.github.horaciocome1.witweather.util.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    private val sharedViewModel: HomeSharedViewModel by viewModels()

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val snackbarError: Snackbar by lazy {
        Snackbar.make(binding.root, getString(R.string.location_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry)) {
                    snackbarError.dismiss()
                    snackbarLoading.show()
                    resetLocationCallback()
                }
    }

    private val snackbarLoading: Snackbar by lazy {
        Snackbar.make(binding.root, getString(R.string.location_loading), Snackbar.LENGTH_SHORT)
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
                    if (location.latitude == 0.0 && location.longitude == 0.0) {
                        snackbarError.show()
                    } else {
                        sharedViewModel.setGeoCoordinates(location.latitude, location.longitude)
                    }
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            sharedViewModel.setLocationPermissionGranted(false)
            return
        }
        sharedViewModel.setLocationPermissionGranted(true)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
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
        Log.d("ActivityMain", "onRequestPermissionsResult: $requestCode\n$permissions\n$grantResults")
        when (requestCode) {
            Constants.REQUEST_CODE_LOCATION_PERMISSIONS -> {
                val isGranted = grantResults.first() == PackageManager.PERMISSION_GRANTED
                if (isGranted) {
                    Log.d("ActivityMain", "onRequestPermissionsResult: GRANTED")
                    sharedViewModel.setLocationPermissionGranted(true)
                    return
                }
                Log.d("ActivityMain", "onRequestPermissionsResult: DENIED")
                sharedViewModel.setLocationPermissionGranted(false)
            }
        }
    }

    private fun managePermissionRequests() {
        sharedViewModel.isLocationPermissionRequested.observe(this) {
            Log.d("ActivityMain", "onStart: $it")
            if (it) {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Constants.REQUEST_CODE_LOCATION_PERMISSIONS
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun resetLocationCallback() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}