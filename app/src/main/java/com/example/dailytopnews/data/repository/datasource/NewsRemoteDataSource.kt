package com.example.dailytopnews.data.repository.datasource

import com.example.dailytopnews.data.model.ApiResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country: String, pageSize: Int): Response<ApiResponse>
    suspend fun getSearchedHeadlines(country: String, searchQuery: String, pageSize: Int): Response<ApiResponse>
}