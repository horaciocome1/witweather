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

package io.github.horaciocome1.storage.extensions

import java.util.Calendar
import java.util.Date

private const val KELVIN: Double = 273.15

fun Double.asCelsius(): Double =
    this - KELVIN

fun Long.asSunrisePST(): String {
    val date = Date(this * 1000L)
    val calendar = Calendar.getInstance()
    calendar.time = date
    return "${calendar[Calendar.HOUR_OF_DAY]}:${calendar[Calendar.MINUTE]}"
}
