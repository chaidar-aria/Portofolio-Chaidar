package com.chaidar.savedisasterportofolio.network.weather

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfigWeather {
    private const val baseURL = "https://api.openweathermap.org/data/2.5/"

    private fun getRetrofitWeather(): Retrofit {
        val client = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getServiceWeather(): ApiServiceWeather {
        return getRetrofitWeather().create(ApiServiceWeather::class.java)
    }
}
