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

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesWeatherRepository(
    private val citiesWeatherService: CitiesWeatherService
) {

    suspend fun getCityWeather(
        latitude: Double,
        longitude: Double,
    ): CityWeather =
        withContext(Dispatchers.IO) {
            return@withContext citiesWeatherService.getCityWeather(latitude, longitude)
                .body()!!
                .asCityWeather()
        }

    suspend fun getCityWeather(
        cityId: Int
    ): CityWeather =
        withContext(Dispatchers.IO) {
            return@withContext citiesWeatherService.getCityWeather(cityId)
                .body()!!
                .asCityWeather()
        }
}
