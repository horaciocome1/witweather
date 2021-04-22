package io.github.horaciocome1.witweather.ui.city_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.horaciocome1.witweather.data.city_weather.CitiesWeatherRepository
import io.github.horaciocome1.witweather.data.city_weather.CityWeather
import io.github.horaciocome1.witweather.util.Constants
import io.github.horaciocome1.witweather.util.toCelsius
import kotlinx.coroutines.launch

class CityWeatherViewModel : ViewModel() {

    private val cityWeather: MutableLiveData<CityWeather> = MutableLiveData()

    private var cityId: Int = 0

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
        viewModelScope.launch {
            val weather = CitiesWeatherRepository.getCityWeather(cityId)
            weather.main.toCelsius()
            cityWeather.value = weather
        }
    }

}