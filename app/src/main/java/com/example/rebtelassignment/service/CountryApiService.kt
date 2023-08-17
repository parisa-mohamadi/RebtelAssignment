package com.example.rebtelassignment.service

import com.example.rebtelassignment.model.Country
import retrofit2.http.GET

interface CountryApiService {
    @GET("v3.1/all")
    suspend fun getCountries(): List<Country>
}