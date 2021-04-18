package io.github.horaciocome1.witweather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import io.github.horaciocome1.witweather.data.City
import io.github.horaciocome1.witweather.data.CityWeather

class HomeViewModel : ViewModel() {

    fun getCityWeather(latitude: Float, longitude: Float): LiveData<CityWeather> {

        return MutableLiveData()
    }

    fun getCities(): LiveData<MutableList<City>> {

        return MutableLiveData()
    }

    fun navigateToCity(navController: NavController, cityId: Int, cityName: String) {
        val directions = HomeFragmentDirections.navigateCityWeather(cityId, cityName)
        navController.navigate(directions)
    }

}