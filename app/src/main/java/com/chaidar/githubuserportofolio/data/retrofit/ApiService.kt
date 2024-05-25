package com.chaidar.githubuserportofolio.data.retrofit


import com.chaidar.githubuserportofolio.data.response.DetailResponse
import com.chaidar.githubuserportofolio.data.response.FollowersResponseItem
import com.chaidar.githubuserportofolio.data.response.FollowingResponseItem
import com.chaidar.githubuserportofolio.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<FollowersResponseItem>>


    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowingResponseItem>>

}