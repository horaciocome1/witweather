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

package io.github.horaciocome1.network.repositories

import io.github.horaciocome1.network.api.CitiesWeatherService
import io.github.horaciocome1.network.api.response.CityWeatherResponse
import io.github.horaciocome1.network.util.MyNetworkCallResult
import io.github.horaciocome1.network.util.MyNetworkRequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesWeatherRepository(
    private val citiesWeatherService: CitiesWeatherService
) {

    suspend fun getCityWeather(
        latitude: Double,
        longitude: Double,
    ): MyNetworkRequestResult<CityWeatherResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val weather = citiesWeatherService.getCityWeather(latitude, longitude)
                    .body()
                if (weather != null) {
                    MyNetworkRequestResult.success(weather)
                } else {
                    MyNetworkRequestResult.error(
                        MyNetworkCallResult.ERROR
                    )
                }
            } catch (e: Exception) {
                MyNetworkRequestResult.error(
                    MyNetworkCallResult.ERROR_OUT_OF_REACH
                )
            }
        }

    suspend fun getCityWeather(
        cityId: Int
    ): MyNetworkRequestResult<CityWeatherResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val weather = citiesWeatherService.getCityWeather(cityId)
                    .body()
                if (weather != null) {
                    MyNetworkRequestResult.success(weather)
                } else {
                    MyNetworkRequestResult.error(
                        MyNetworkCallResult.ERROR
                    )
                }
            } catch (e: Exception) {
                MyNetworkRequestResult.error(
                    MyNetworkCallResult.ERROR_OUT_OF_REACH
                )
            }
        }
}
