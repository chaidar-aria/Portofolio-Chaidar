package com.chaidar.savedisasterportofolio.network.weather

import com.chaidar.savedisasterportofolio.model.response.OpenWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceWeather {
    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Call<OpenWeatherResponse>
}