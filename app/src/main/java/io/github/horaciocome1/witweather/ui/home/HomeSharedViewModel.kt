package io.github.horaciocome1.witweather.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.horaciocome1.witweather.data.city_weather.GeoCoordinates

class HomeSharedViewModel : ViewModel() {

    private val _isLocationPermissionRequested: MutableLiveData<Boolean> = MutableLiveData()

    val isLocationPermissionRequested: LiveData<Boolean>
        get() { return _isLocationPermissionRequested }

    private val _isLocationPermissionGranted: MutableLiveData<Boolean> = MutableLiveData()

    val isLocationPermissionGranted: LiveData<Boolean>
        get() {return  _isLocationPermissionGranted}

    private val _geoCoordinates: MutableLiveData<GeoCoordinates> = MutableLiveData()

    val geoCoordinates: LiveData<GeoCoordinates>
        get() { return _geoCoordinates }

    fun requestLocationPermission() {
        _isLocationPermissionRequested.value = true
    }

    fun setLocationPermissionGranted(isGranted: Boolean) {
        _isLocationPermissionGranted.value = isGranted
    }

    fun setGeoCoordinates(latitude: Double, longitude: Double) {
        _geoCoordinates.value = GeoCoordinates(latitude, longitude)
    }

}