package com.chaidar.savedisasterportofolio.network.earthquake

import com.chaidar.savedisasterportofolio.model.response.BmkgEarthQuakeResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceEarthQuake {

    @GET("autogempa.json")
    fun getEarthquakeEarthQuake(): Call<BmkgEarthQuakeResponse>
}