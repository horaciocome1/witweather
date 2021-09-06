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

package io.github.horaciocome1.storage.repositories

import io.github.horaciocome1.storage.api.LocalCacheService
import io.github.horaciocome1.storage.util.MyStorageCallResult
import io.github.horaciocome1.storage.util.MyStorageRequestResult
import io.realm.RealmModel

class LocalCacheRepository(
    val service: LocalCacheService
) {

    fun <T : RealmModel> setCityWeather(
        cityWeather: T
    ): MyStorageRequestResult<T> = try {
        service.setCityWeather(cityWeather)
        MyStorageRequestResult.success()
    } catch (e: Exception) {
        MyStorageRequestResult.error(
            MyStorageCallResult.ERROR
        )
    }

    inline fun <reified T : RealmModel> getCityWeather(
        cityId: Int
    ): MyStorageRequestResult<T> = try {
        val cityWeather = service.getCityWeather<T>(cityId)
        if (cityWeather != null) {
            MyStorageRequestResult.success()
        } else {
            MyStorageRequestResult.error(
                MyStorageCallResult.ERROR_NOT_FOUND
            )
        }
    } catch (e: Exception) {
        MyStorageRequestResult.error(
            MyStorageCallResult.ERROR
        )
    }
}
