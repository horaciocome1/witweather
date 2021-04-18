package io.github.horaciocome1.witweather.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import io.github.horaciocome1.witweather.data.cities.City
import io.github.horaciocome1.witweather.data.city_weather.CityWeather
import io.github.horaciocome1.witweather.data.cities.CitiesRepository
import io.github.horaciocome1.witweather.data.city_weather.CitiesWeatherRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val cities: MutableLiveData<MutableList<City>> = MutableLiveData()

    private val cityWeather: MutableLiveData<CityWeather> = MutableLiveData()

    fun getCities(): LiveData<MutableList<City>> {
        if (!cities.value.isNullOrEmpty()) {
            return cities
        }
        viewModelScope.launch { cities.value = CitiesRepository.getCities() }
        return cities
    }

    fun getCityWeather(latitude: Float, longitude: Float): LiveData<CityWeather> {
        viewModelScope.launch { cityWeather.value = CitiesWeatherRepository.getCityWeather(latitude, longitude) }
        return cityWeather
    }

    fun navigateToCity(navController: NavController, cityId: Int, cityName: String) {
        val directions = HomeFragmentDirections.navigateCityWeather(cityId, cityName)
        navController.navigate(directions)
    }

}