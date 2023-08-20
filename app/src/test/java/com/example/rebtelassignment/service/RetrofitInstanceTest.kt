package com.example.rebtelassignment.service

import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test

class RetrofitInstanceTest {
    private lateinit var retrofitInstance: RetrofitInstance

        @Before
        fun setup() {
            retrofitInstance = RetrofitInstance
        }

        @Test
        fun `countryApiService should not be null`() {
            val countryApiService = retrofitInstance.countryApiService

            assertNotNull(countryApiService)
        }
}
