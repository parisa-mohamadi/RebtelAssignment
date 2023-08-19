package com.example.rebtelassignment.utils

import android.content.Context
import android.widget.Toast
import com.example.rebtelassignment.model.Country
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

class Utils {
    companion object {
        fun getFlagUrl(countryCode: String, jsonObject: JSONObject): String? {
            val countryEntry = jsonObject.optJSONObject(countryCode)
            return countryEntry?.optString("image")
        }

        fun List<Country>.searchCountryList(key: String): MutableList<Country> {
            val tempList = mutableListOf<Country>()
            this.forEach {
                if (it.name.common.lowercase().contains(key.lowercase())) {
                    tempList.add(it)
                }
            }
            return tempList
        }

        fun formatPopulation(population: Long): String {
            val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
            return numberFormat.format(population)
        }

        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
