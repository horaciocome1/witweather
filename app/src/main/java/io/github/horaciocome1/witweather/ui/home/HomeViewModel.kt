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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import io.github.horaciocome1.network.cities.CitiesRepository
import io.github.horaciocome1.network.cities.City
import io.github.horaciocome1.network.city_weather.CitiesWeatherRepository
import io.github.horaciocome1.witweather.data.city_weather.CityWeather
import io.github.horaciocome1.network.city_weather.GeoCoordinates
import io.github.horaciocome1.witweather.util.NetworkCallResult
import io.github.horaciocome1.witweather.util.isEmpty
import kotlinx.coroutines.launch

class HomeViewModel(
    private val citiesRepository: io.github.horaciocome1.network.cities.CitiesRepository,
    private val citiesWeatherRepository: io.github.horaciocome1.network.city_weather.CitiesWeatherRepository
) : ViewModel() {

    private val cities: MutableLiveData<MutableList<io.github.horaciocome1.network.cities.City>> = MutableLiveData()

    private val cityWeather: MutableLiveData<CityWeather> = MutableLiveData()

    private var geoCoordinates: io.github.horaciocome1.network.city_weather.GeoCoordinates =
        io.github.horaciocome1.network.city_weather.GeoCoordinates(0.0, 0.0)

    private val _callResult: MutableLiveData<NetworkCallResult> = MutableLiveData()

    val callResult: LiveData<NetworkCallResult>
        get() {
            return _callResult
        }

    fun getCities(): LiveData<MutableList<io.github.horaciocome1.network.cities.City>> {
        if (!cities.value.isNullOrEmpty()) {
            return cities
        }
        viewModelScope.launch { cities.value = citiesRepository.getCities() }
        return cities
    }

    fun getCityWeather(
        geoCoordinates: io.github.horaciocome1.network.city_weather.GeoCoordinates
    ): LiveData<CityWeather> {
        _callResult.value = NetworkCallResult.LOADING
        return when {
            geoCoordinates.isEmpty() -> cityWeather
            geoCoordinates == this.geoCoordinates -> cityWeather
            else -> {
                this.geoCoordinates = geoCoordinates
                viewModelScope.launch {
                    try {
                        cityWeather.value = citiesWeatherRepository.getCityWeather(
                            this@HomeViewModel.geoCoordinates.latitude,
                            this@HomeViewModel.geoCoordinates.longitude
                        )
                        _callResult.value = NetworkCallResult.SUCCESS_REMOTE
                    } catch (e: Exception) {
                        _callResult.value = NetworkCallResult.ERROR
                    }
                }
                cityWeather
            }
        }
    }

    fun navigateToCity(navController: NavController, cityId: Int, cityName: String) {
        val directions = HomeFragmentDirections.navigateCityWeather(cityId, cityName)
        navController.navigate(directions)
    }
}
