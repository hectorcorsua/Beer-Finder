package com.hectorcorsua.beerfinder.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BeerMethod(
    @SerializedName("mash_temp") val mashTemp : List<TempTime>,
    val fermentation : Fermentation,
    val twist : String
) : Serializable

data class TempTime(
    val temp : Units,
    val duration : Double
) : Serializable

data class Fermentation(
    val temp: Units
) : Serializable
