package com.chaidar.savedisasterportofolio.network.earthquake

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfigEarthQuake {

    // Ganti dengan URL yang sesuai
    private const val baseURL = "https://data.bmkg.go.id/DataMKG/TEWS/"

    private fun getRetrofitEarthQuake(): Retrofit {

        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                // Ganti dengan mekanisme penyimpanan kunci API yang lebih aman
//                .addHeader("X-RapidAPI-Key", "89bb0478aamsh0bcbdd01be5abdfp149f0ejsn5b83f6311b6a")
                .build()
            chain.proceed(requestHeaders)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getServiceEarthQuake(): ApiServiceEarthQuake {
        return getRetrofitEarthQuake().create(ApiServiceEarthQuake::class.java)
    }
}