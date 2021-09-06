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

package io.github.horaciocome1.network.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityWeatherResponse(
    var id: Int = 0,
    var name: String = "",
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
