package io.github.horaciocome1.witweather.data

import io.github.horaciocome1.witweather.data.city_weather.CitiesWeatherService
import io.github.horaciocome1.witweather.data.city_weather.CityWeatherJsonAdapter
import io.github.horaciocome1.witweather.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Network {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val citiesWeatherService: CitiesWeatherService by lazy {
        retrofit.create(CitiesWeatherService::class.java)
    }

}