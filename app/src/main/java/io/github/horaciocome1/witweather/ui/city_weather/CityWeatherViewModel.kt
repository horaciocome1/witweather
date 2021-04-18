package io.github.horaciocome1.witweather.ui.city_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.horaciocome1.witweather.data.CityWeather

class CityWeatherViewModel : ViewModel() {

    fun getCityWeather(cityId: Int): LiveData<CityWeather> {

        return MutableLiveData()
    }

}