package com.brhmty.dailytopnews.data.repository.datasourceimpl

import com.brhmty.dailytopnews.data.api.NewsApiService
import com.brhmty.dailytopnews.data.model.ApiResponse
import com.brhmty.dailytopnews.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsApiService,
    ): NewsRemoteDataSource {

    override suspend fun getTopHeadlines(
        country: String,
        pageSize: Int
    ): Response<ApiResponse> {
        return newsApiService.getTopHeadlines(country, pageSize)
    }

    override suspend fun getSearchedHeadlines(
        country: String,
        searchQuery: String,
        pageSize: Int,
    ): Response<ApiResponse> {
        return newsApiService.getSearchHeadlines(country, searchQuery, pageSize)
    }
}