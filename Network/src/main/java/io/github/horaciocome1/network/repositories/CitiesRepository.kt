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

import io.github.horaciocome1.network.api.CitiesServiceImpl
import io.github.horaciocome1.network.model.City
import io.github.horaciocome1.network.util.MyNetworkCallResult
import io.github.horaciocome1.network.util.MyNetworkRequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesRepository(
    private val service: CitiesServiceImpl
) {

    suspend fun getCities(): MyNetworkRequestResult<MutableList<City>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val cities = service.getCities()
                MyNetworkRequestResult.success(cities)
            } catch (e: Exception) {
                MyNetworkRequestResult.error(
                    MyNetworkCallResult.ERROR
                )
            }
        }
}
