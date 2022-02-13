package com.example.dailytopnews.data.repository

import com.example.dailytopnews.data.model.ApiResponse
import com.example.dailytopnews.data.model.Article
import com.example.dailytopnews.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadlines(country: String, pageSize: Int): Resource<ApiResponse>
    suspend fun getSearchedNews(country: String, searchingQuery: String, pageSize: Int): Resource<ApiResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteSavedNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>

}