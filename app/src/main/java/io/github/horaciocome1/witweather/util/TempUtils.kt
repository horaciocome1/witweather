package io.github.horaciocome1.witweather.util

import io.github.horaciocome1.witweather.data.city_weather.Main
import io.github.horaciocome1.witweather.data.city_weather.Sys
import java.util.*

fun Main.toCelsius() = apply {
    temp -= Constants.KELVIN
    tempMin -= Constants.KELVIN
    tempMax -= Constants.KELVIN
    feelsLike -= Constants.KELVIN
}

fun Sys.toSunrisePST(): String {
    val date = Date(sunrise * 1000L)
    val calendar = Calendar.getInstance()
    calendar.time = date
    return "${calendar[Calendar.HOUR_OF_DAY]}:${calendar[Calendar.MINUTE]}"
}