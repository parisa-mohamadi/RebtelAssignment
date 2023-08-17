package com.example.rebtelassignment.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") val name: Name,
    @SerializedName("capital") val capital: List<String>,
    @SerializedName("population") val population: Int,
    @SerializedName("currencies") val currencies: Map<String, Currency>,
    @SerializedName("languages") val languages: Map<String, String>,
    @SerializedName("flag") val flag: String,
    // we can Add more properties if needed
)

data class Name(
    @SerializedName("common") val common: String,
    @SerializedName("official") val official: String,
    @SerializedName("nativeName") val nativeName: NativeName
)

data class NativeName(
    @SerializedName("ara") val ara: Ara
)

data class Ara(
    @SerializedName("official") val official: String,
    @SerializedName("common") val common: String
)

data class Currency(
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String
)
