package io.github.horaciocome1.witweather.data.city_weather

interface CitiesWeatherServiceInterface {

    suspend fun getCityWeather(latitude: Float, longitude: Float): CityWeather

    suspend fun getCityWeather(cityId: Int): CityWeather

}