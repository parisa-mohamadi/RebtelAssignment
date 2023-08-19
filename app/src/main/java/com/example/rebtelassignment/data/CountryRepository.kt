package com.example.rebtelassignment.data

import com.example.rebtelassignment.model.Country
import com.example.rebtelassignment.service.RetrofitInstance

/**
 * Repository class responsible for fetching country data from the API using the provided
 * [CountryApiService] instance from [RetrofitInstance].
 */
class CountryRepository {
    private val countryApiService = RetrofitInstance.countryApiService

    suspend fun getCountries(): List<Country> {
        return countryApiService.getCountries()
    }
}