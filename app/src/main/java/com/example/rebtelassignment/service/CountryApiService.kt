package com.example.rebtelassignment.service

import com.example.rebtelassignment.model.Country
import retrofit2.http.GET

/**
 * Retrofit interface defining the API endpoints for fetching countries' information.
 */
interface CountryApiService {
    @GET("v3.1/all")
    suspend fun getCountries(): List<Country>
}