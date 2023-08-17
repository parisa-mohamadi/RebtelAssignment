package com.example.rebtelassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebtelassignment.model.Country
import com.example.rebtelassignment.data.CountryRepository
import kotlinx.coroutines.launch


class CountryInfoViewModel : ViewModel() {
    private val repository = CountryRepository()

    private val _countries = MutableLiveData<List<Country>>(emptyList())
    val countries: LiveData<List<Country>> = _countries

    init {
        fetchCountries()
    }

     fun fetchCountries() {
        viewModelScope.launch {
            try {
                val response = repository.getCountries()
                _countries.value = response
                Log.e("tag","response: "+_countries.value.toString())
            } catch (e: Exception) {
                // Handle error
                Log.e("tag","error: "+e.message.toString())
            }
        }
    }
}
