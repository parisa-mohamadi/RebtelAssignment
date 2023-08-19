package com.example.rebtelassignment.service

import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Retrofit

class RetrofitInstanceTest {
    private lateinit var retrofitInstance: RetrofitInstance

    @Before
    fun setup() {
        retrofitInstance = RetrofitInstance
    }

    @Test
    fun `countryApiService should not be null`() {
        val mockRetrofit: Retrofit = mock()
        val mockCountryApiService: CountryApiService = mock()

        whenever(mockRetrofit.create(CountryApiService::class.java)).thenReturn(mockCountryApiService)

        val retrofitField = RetrofitInstance::class.java.getDeclaredField("retrofit")
        retrofitField.isAccessible = true
        retrofitField.set(retrofitInstance, mockRetrofit)

        val countryApiService = retrofitInstance.countryApiService

        assertNotNull(countryApiService)
    }

}
