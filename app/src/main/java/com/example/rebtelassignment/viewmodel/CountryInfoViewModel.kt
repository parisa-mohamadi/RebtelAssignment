package com.example.rebtelassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebtelassignment.data.CountryRepository
import com.example.rebtelassignment.model.Country
import kotlinx.coroutines.launch

/**
 * ViewModel class responsible for managing country information data and interactions.
 *
 * This ViewModel fetches country data from the repository and exposes it as LiveData.
 */
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
                Log.e("tag","error: "+e.message.toString())
            }
        }
    }
}
