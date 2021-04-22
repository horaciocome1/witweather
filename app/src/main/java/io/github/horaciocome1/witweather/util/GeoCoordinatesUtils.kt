package io.github.horaciocome1.witweather.util

import io.github.horaciocome1.witweather.data.city_weather.GeoCoordinates

fun GeoCoordinates.isEmpty(): Boolean {
    return latitude == 0.0 && longitude == 0.0
}