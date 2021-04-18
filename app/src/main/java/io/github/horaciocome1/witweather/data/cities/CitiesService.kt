package io.github.horaciocome1.witweather.data.cities

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CitiesService : CitiesServiceInterface {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getCities(): MutableList<City> {
        return Gson().fromJson(
            CitiesFakeDataSource.json,
            object : TypeToken<MutableList<City?>?>() {}.type
        )
    }

}