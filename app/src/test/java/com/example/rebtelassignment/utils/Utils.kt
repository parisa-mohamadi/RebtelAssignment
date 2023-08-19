package com.example.rebtelassignment.utils

import com.example.rebtelassignment.model.Ara
import com.example.rebtelassignment.model.Country
import com.example.rebtelassignment.model.Currency
import com.example.rebtelassignment.model.Name
import com.example.rebtelassignment.model.NativeName
import com.example.rebtelassignment.utils.Utils.Companion.searchCountryList
import org.junit.Assert.assertEquals
import org.junit.Test


class UtilsTest {
    @Test
    fun `searchCountryList should return filtered country list`() {
        // Given
        val key = "united"
        val countries = listOf(
            Country(
                name = Name(common="United States", official="Svalbard og Jan Mayen", nativeName= NativeName(
                    Ara("official name", "common name")
                )
                ),
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

        // When
        val result = countries.searchCountryList(key)

        // Then
        assertEquals(1, result.size)
        assertEquals("United States", result[0].name.common)
    }
}
