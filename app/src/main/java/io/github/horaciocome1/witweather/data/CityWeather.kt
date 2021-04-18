package io.github.horaciocome1.witweather.data

data class CityWeather(
    var cityName: String = "",
    var main: String = "",
    var temp: Temp = Temp(),
    var sunrise: Float = -1f,
    var wind: Float = -1f,
    var humidity: Float = -1f
)