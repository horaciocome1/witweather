package io.github.horaciocome1.witweather.data.city_weather

import io.github.horaciocome1.witweather.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesWeatherService {

    @GET("weather")
    suspend fun getCityWeather(
            @Query("lat") latitude: Double,
            @Query("lon") longitude: Double,
            @Query("appid") apiKey: String = Constants.API_KEY
    ): Response<CityWeather>

    @GET("weather")
    suspend fun getCityWeather(
        @Query("id") cityId: Int,
        @Query("appid") apiKey: String = Constants.API_KEY
    ): Response<CityWeather>

}