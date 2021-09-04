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

interface LocalCacheService {

    /**
     * deletes existing object and save new to local database
     * @param cityWeather is the weather to cache
     */
    fun setCityWeather(
        cityWeather: CityWeather
    )

    /**
     * retrieves from local database existing weather data
     * @return is null if there is no cached weather data
     */
    fun getCityWeather(
        cityId: Int
    ): CityWeather?
}
