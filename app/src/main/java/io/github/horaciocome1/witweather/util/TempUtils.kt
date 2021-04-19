package io.github.horaciocome1.witweather.util

import io.github.horaciocome1.witweather.data.city_weather.Main

fun Main.toCelsius() = apply {
    temp -= Constants.KELVIN
    tempMin -= Constants.KELVIN
    tempMax -= Constants.KELVIN
    feelsLike -= Constants.KELVIN
}