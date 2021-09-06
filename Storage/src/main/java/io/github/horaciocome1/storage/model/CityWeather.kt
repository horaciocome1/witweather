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

package io.github.horaciocome1.storage.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

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
