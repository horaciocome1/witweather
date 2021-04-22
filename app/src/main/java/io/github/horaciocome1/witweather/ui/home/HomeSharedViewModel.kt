/*
 * Copyright 2021 Horácio Flávio Comé Júnior
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.horaciocome1.witweather.ui.home

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