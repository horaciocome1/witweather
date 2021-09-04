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

package io.github.horaciocome1.witweather.di

import io.github.horaciocome1.network.cities.CitiesRepository
import io.github.horaciocome1.network.cities.CitiesService
import io.github.horaciocome1.network.city_weather.CitiesWeatherRepository
import io.github.horaciocome1.network.city_weather.CitiesWeatherService
import io.github.horaciocome1.storage.LocalCacheRepository
import io.github.horaciocome1.storage.LocalCacheServiceImpl
import io.github.horaciocome1.witweather.ui.city_weather.CityWeatherViewModel
import io.github.horaciocome1.witweather.ui.home.HomeViewModel
import io.github.horaciocome1.witweather.util.Constants
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val dataSourceModule = module {

    // region retrofit
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create()
            ).build()
    }
    // endregion retrofit

    // region cities
    factory {
        io.github.horaciocome1.network.cities.CitiesService()
    }
    single {
        io.github.horaciocome1.network.cities.CitiesRepository(get())
    }
    // endregion cities

    // region city's weather
    factory {
        get<Retrofit>().create<io.github.horaciocome1.network.city_weather.CitiesWeatherService>()
    }
    single {
        io.github.horaciocome1.network.city_weather.CitiesWeatherRepository(get())
    }
    // endregion city's weather

    // region local cache
    factory {
        io.github.horaciocome1.storage.LocalCacheServiceImpl()
    }
    single {
        io.github.horaciocome1.storage.LocalCacheRepository(get())
    }
    // endregion local cache
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        CityWeatherViewModel(get(), get())
    }
}
