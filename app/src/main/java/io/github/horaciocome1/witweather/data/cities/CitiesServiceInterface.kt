package io.github.horaciocome1.witweather.data.cities

interface CitiesServiceInterface {

    suspend fun getCities(): MutableList<City>

}