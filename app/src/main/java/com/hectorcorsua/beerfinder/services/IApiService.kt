package com.hectorcorsua.beerfinder.services

import com.hectorcorsua.beerfinder.models.Beer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IApiService {
    @GET("beers")
    fun getBeers(@Query("page") page : Int, @Query("beer_name") name:String): Call<List<Beer>>

    @GET("beers/{id}")
    fun getBeerDetail(@Path("id") id: Int) : Call<List<Beer>>
}