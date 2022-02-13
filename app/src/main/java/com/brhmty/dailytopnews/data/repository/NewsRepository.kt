package com.brhmty.dailytopnews.data.repository

import com.brhmty.dailytopnews.data.model.ApiResponse
import com.brhmty.dailytopnews.data.model.Article
import com.brhmty.dailytopnews.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadlines(country: String, pageSize: Int): Resource<ApiResponse>
    suspend fun getSearchedNews(country: String, searchingQuery: String, pageSize: Int): Resource<ApiResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteSavedNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>

}