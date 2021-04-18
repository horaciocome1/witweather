package io.github.horaciocome1.witweather.data.city_weather

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CitiesWeatherRepository : CitiesWeatherServiceInterface {

    private val service: CitiesWeatherService by lazy { CitiesWeatherService() }

    override suspend fun getCityWeather(latitude: Float, longitude: Float): CityWeather =
        withContext(Dispatchers.IO) {
            service.getCityWeather(latitude, longitude)
        }

    override suspend fun getCityWeather(cityId: Int): CityWeather =
        withContext(Dispatchers.IO) {
            service.getCityWeather(cityId)
        }

}