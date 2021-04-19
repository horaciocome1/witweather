package io.github.horaciocome1.witweather.data.city_weather

import io.github.horaciocome1.witweather.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CitiesWeatherService {

    @GET("data/2.5/weather?lat={lat}&lon={lon}&appid=${Constants.API_KEY}")
    suspend fun getCityWeather(
        @Path("lat") latitude: Float,
        @Path("lon") longitude: Float
    ): Response<CityWeather>

//    @GET("data/2.5/weather?id={cityId}&appid=${Constants.API_KEY}")
    @GET("weather")
    suspend fun getCityWeather(
        @Query("id") cityId: Int,
        @Query("appid") apiKey: String = Constants.API_KEY
    ): Response<CityWeather>

}