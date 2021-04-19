package io.github.horaciocome1.witweather.data.city_weather

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Temp(
    var current: Float = -1f,
    var min: Float = -1f,
    var max: Float = -1f,
    var realFeel: Float = -1f
)