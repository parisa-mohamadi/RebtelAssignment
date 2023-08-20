package com.example.rebtelassignment.service

import com.example.rebtelassignment.model.Ara
import com.example.rebtelassignment.model.Country
import com.example.rebtelassignment.model.Currency
import com.example.rebtelassignment.model.Name
import com.example.rebtelassignment.model.NativeName
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CountryApiServiceTest {
    private lateinit var countryApiService: CountryApiService

    @Before
    fun setup() {
        countryApiService = mockk()
    }

    @Test
    fun `getCountries should return a list of countries`() {
        val mockCountries = listOf(
            Country(
                name = Name(common="United States", official="United States", nativeName= NativeName(
                            Ara("official name", "common name"))),
                capital = listOf("Washington, D.C."),
                population = 1000,
                currencies = mapOf(
                    "USD" to Currency(
                        name = "United States Dollar",
                        symbol = "$"
                    )
                ),
                languages = mapOf("lan1" to "lan2"),
                flag = "flag",
                cca2 = "cca2"

            ),

        )
        coEvery { countryApiService.getCountries() } returns mockCountries

        val result = runBlocking { countryApiService.getCountries() }

        assertEquals(mockCountries, result)
    }
}
