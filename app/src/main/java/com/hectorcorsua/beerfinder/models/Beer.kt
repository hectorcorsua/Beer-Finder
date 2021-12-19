package com.hectorcorsua.beerfinder.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Beer(
    val id : Int,
    val name: String,
    val description: String,
    @SerializedName("image_url")val image: String,
    val abv: Double,
    val ibu: Double,
    val ebc: Double,
    val ph: Double,
    val tagline: String,
    @SerializedName("first_brewed") val firstBrewed : String,
    @SerializedName("target_fg") val finalGravity : Double,
    @SerializedName("target_og") val originalGravity : Double,
    val srm : Double,
    @SerializedName("attenuation_level") val attenuationLevel : Double,
    val volume : Units,
    @SerializedName("boil_volume") val boilVolume : Units,
    val method : BeerMethod,
    @SerializedName("food_pairing") val foodPairing : List<String>,
    @SerializedName("brewers_tips") val brewersTips: String,
    @SerializedName("contributed_by") val contributor : String
) : Serializable