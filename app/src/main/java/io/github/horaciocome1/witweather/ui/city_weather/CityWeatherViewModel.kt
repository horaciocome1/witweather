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

package io.github.horaciocome1.witweather.ui.city_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.horaciocome1.witweather.data.city_weather.CitiesWeatherRepository
import io.github.horaciocome1.witweather.data.city_weather.CityWeather
import io.github.horaciocome1.witweather.util.Constants
import io.github.horaciocome1.witweather.util.NetworkCallResult
import io.github.horaciocome1.witweather.util.toCelsius
import kotlinx.coroutines.launch

class CityWeatherViewModel : ViewModel() {

    private val cityWeather: MutableLiveData<CityWeather> = MutableLiveData()

    private var cityId: Int = 0

    private val _callResult: MutableLiveData<NetworkCallResult> = MutableLiveData()

    val callResult: LiveData<NetworkCallResult>
        get() { return _callResult }

    fun getCityWeather(cityId: Int): LiveData<CityWeather> {
        this.cityId = cityId
        fetchWeather()
        return cityWeather
    }

    fun refreshWeather() {
        if (cityId != 0) {
            fetchWeather()
        }
    }

    private fun fetchWeather() {
        _callResult.value = NetworkCallResult.SUCCESS
        viewModelScope.launch {
            try {
                val weather = CitiesWeatherRepository.getCityWeather(cityId)
                weather.main.toCelsius()
                cityWeather.value = weather
                _callResult.value = NetworkCallResult.SUCCESS
            } catch (e: Exception) {
                _callResult.value = NetworkCallResult.ERROR
            }
        }
    }

}