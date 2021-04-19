package io.github.horaciocome1.witweather.data.city_weather

import android.util.Log
import io.github.horaciocome1.witweather.data.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CitiesWeatherRepository {

    private val service: CitiesWeatherService by lazy {
        Network.citiesWeatherService
    }

    suspend fun getCityWeather(latitude: Float, longitude: Float): CityWeather =
        withContext(Dispatchers.IO) {
            val response = service.getCityWeather(latitude, longitude)
            Log.d("CitiesWeatherR", "getCityWeather: $response")
            return@withContext response.body()!!
        }

    suspend fun getCityWeather(cityId: Int): CityWeather =
        withContext(Dispatchers.IO) {
            val response = service.getCityWeather(cityId)
            Log.d("CitiesWeatherR", "getCityWeather: $response")
            return@withContext response.body()!!
        }

}