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

package io.github.horaciocome1.witweather.extensions

import io.github.horaciocome1.storage.extensions.asCelsius
import io.github.horaciocome1.storage.extensions.asSunrisePST
import io.github.horaciocome1.storage.model.CityWeather
import io.github.horaciocome1.network.city_weather.CityWeatherResponse
import java.util.Calendar

fun io.github.horaciocome1.network.city_weather.CityWeatherResponse.asCityWeather(): CityWeather =
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
