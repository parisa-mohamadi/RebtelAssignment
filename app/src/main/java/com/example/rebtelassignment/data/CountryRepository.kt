package com.example.rebtelassignment.data

import com.example.rebtelassignment.model.Country
import com.example.rebtelassignment.service.RetrofitInstance

class CountryRepository {
    private val countryApiService = RetrofitInstance.countryApiService

    suspend fun getCountries(): List<Country> {
        return countryApiService.getCountries()
    }
}