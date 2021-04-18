package io.github.horaciocome1.witweather.data.cities

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CitiesRepository : CitiesServiceInterface {

    private val service: CitiesService by lazy { CitiesService() }

    override suspend fun getCities(): MutableList<City> =
        withContext(Dispatchers.IO) {
            service.getCities()
        }

}