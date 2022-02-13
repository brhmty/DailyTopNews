package com.brhmty.dailytopnews.data.api


import com.brhmty.dailytopnews.BuildConfig
import com.brhmty.dailytopnews.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = BuildConfig.Api_Key,
    ): Response<ApiResponse>

    @GET("/v2/top-headlines")
    suspend fun getSearchHeadlines(
        @Query("country") country: String,
        @Query("q") searchQuery: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = BuildConfig.Api_Key,
    ): Response<ApiResponse>
}