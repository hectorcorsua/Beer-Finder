package com.hectorcorsua.beerfinder.services

import com.hectorcorsua.beerfinder.models.Beer
import com.hectorcorsua.beerfinder.services.callbacks.BeerCallback
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BeerService {

    companion object{
        private var retrofit: Retrofit? = null
        private val beerService: BeerService? = null

        fun getInstance(): BeerService {
            return beerService ?: BeerService()
        }

        private fun initializeRetrofit(): Retrofit {
            val httpClientBuilder = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClientBuilder.addInterceptor(interceptor)
            val okHttpClient: OkHttpClient = httpClientBuilder
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
                .baseUrl("https://api.punkapi.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
    }

    init {
        retrofit = initializeRetrofit()
    }

    fun getBeers(page: Int, searchParam : String, beersCallback: BeerCallback) {
        val service: IApiService = retrofit!!.create(IApiService::class.java)
        service.getBeers(page, searchParam)
            .enqueue(object : Callback<List<Beer>?> {
                override fun onResponse(
                    call: Call<List<Beer>?>,
                    response: Response<List<Beer>?>
                ) {
                    if (response.body() != null) {
                        beersCallback.onSuccess(response.body())
                    } else {
                        beersCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<List<Beer>?>, t: Throwable) {
                    beersCallback.onError("Request error: ${t.message}")
                }
            })
    }

    fun getBeerDetail(id : Int, beerCallback : BeerCallback){
        val service: IApiService = retrofit!!.create(IApiService::class.java)
        service.getBeerDetail(id)
            .enqueue(object : Callback<List<Beer>?> {
                override fun onResponse(
                    call: Call<List<Beer>?>,
                    response: Response<List<Beer>?>
                ) {
                    if (response.body() != null) {
                        beerCallback.onSuccess(response.body())
                    } else {
                        beerCallback.onError(response.message())
                    }
                }

                override fun onFailure(call: Call<List<Beer>?>, t: Throwable) {
                    beerCallback.onError("Request error: ${t.message}")
                }
            })
    }
}