package io.github.horaciocome1.witweather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import io.github.horaciocome1.witweather.data.cities.City
import io.github.horaciocome1.witweather.data.cities.CitiesRepository
import io.github.horaciocome1.witweather.data.city_weather.CitiesWeatherRepository
import io.github.horaciocome1.witweather.data.city_weather.CityWeather
import io.github.horaciocome1.witweather.data.city_weather.GeoCoordinates
import io.github.horaciocome1.witweather.util.isEmpty
import io.github.horaciocome1.witweather.util.toCelsius
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val cities: MutableLiveData<MutableList<City>> = MutableLiveData()

    private val cityWeather: MutableLiveData<CityWeather> = MutableLiveData()

    private var geoCoordinates: GeoCoordinates = GeoCoordinates(0.0, 0.0)

    fun getCities(): LiveData<MutableList<City>> {
        if (!cities.value.isNullOrEmpty()) {
            return cities
        }
        viewModelScope.launch { cities.value = CitiesRepository.getCities() }
        return cities
    }

    fun getCityWeather(geoCoordinates: GeoCoordinates): LiveData<CityWeather> {
        return when {
            geoCoordinates.isEmpty() -> cityWeather
            geoCoordinates == this.geoCoordinates -> cityWeather
            else -> {
                this.geoCoordinates = geoCoordinates
                viewModelScope.launch {
                    val weather = CitiesWeatherRepository.getCityWeather(
                            this@HomeViewModel.geoCoordinates.latitude,
                            this@HomeViewModel.geoCoordinates.longitude
                    )
                    weather.main.toCelsius()
                    cityWeather.value = weather
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