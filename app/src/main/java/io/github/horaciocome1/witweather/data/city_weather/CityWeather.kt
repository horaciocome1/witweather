/*
 * Copyright 2021 Horácio Flávio Comé Júnior
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.horaciocome1.witweather.data.city_weather

import io.github.horaciocome1.witweather.util.Constants
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Calendar
import java.util.Date

open class CityWeather(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var weatherMain: String = "",
    var temp: Double = 0.0,
    var tempMin: Double = 0.0,
    var tempMax: Double = 0.0,
    var feelsLike: Double = 0.0,
    var windSpeed: Double = 0.0,
    var sysSunrise: String = "",
    var timeInMillis: Long = 0
) : RealmObject()

fun CityWeatherResponse.asCityWeather(): CityWeather =
    CityWeather(
        id = id,
        name = name,
        weatherMain = weather.first().main,
        temp = main.temp.asCelsius(),
        tempMin = main.tempMin.asCelsius(),
        tempMax = main.tempMax.asCelsius(),
        feelsLike = main.feelsLike.asCelsius(),
        windSpeed = wind.speed,
        sysSunrise = sys.sunrise.asSunrisePST(),
        timeInMillis = Calendar.getInstance().timeInMillis
    )

private fun Double.asCelsius(): Double =
    this - Constants.KELVIN

private fun Long.asSunrisePST(): String {
    val date = Date(this * 1000L)
    val calendar = Calendar.getInstance()
    calendar.time = date
    return "${calendar[Calendar.HOUR_OF_DAY]}:${calendar[Calendar.MINUTE]}"
}
