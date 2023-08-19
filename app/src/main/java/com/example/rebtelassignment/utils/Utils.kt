package com.example.rebtelassignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.rebtelassignment.model.Country
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

/**
 * this class has useful functions which is used by the other classes.
 */

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

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkNetworkAvailability(connectivityManager)
            } else {
                @Suppress("DEPRECATION")
                val networkInfo = connectivityManager.activeNetworkInfo
                networkInfo != null && networkInfo.isConnected
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        private fun checkNetworkAvailability(connectivityManager: ConnectivityManager): Boolean {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        }
    }
}
