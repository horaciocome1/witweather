package io.github.horaciocome1.witweather.data.city_weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityWeather(
    var weather: MutableList<Weather> = mutableListOf(),
    var main: Main = Main(),
    var wind: Wind = Wind(),
    var sys: Sys = Sys()
)

@JsonClass(generateAdapter = true)
data class Weather(
    var main: String = ""
)

@JsonClass(generateAdapter = true)
data class Main(
    var temp: Double = 0.0,
    @Json(name = "feels_like") var feelsLike: Double = 0.0,
    @Json(name = "temp_min") var tempMin: Double = 0.0,
    @Json(name = "temp_max") var tempMax: Double = 0.0,
)

@JsonClass(generateAdapter = true)
data class Wind(
    var speed: Double = 0.0
)

@JsonClass(generateAdapter = true)
data class Sys(
    var sunrise: Long = 0
)