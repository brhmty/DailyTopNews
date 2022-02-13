package com.brhmty.dailytopnews.domain.usecase

import com.brhmty.dailytopnews.data.model.ApiResponse
import com.brhmty.dailytopnews.data.repository.NewsRepository
import com.brhmty.dailytopnews.domain.util.Resource

class UseCaseGetSearchedNewsHeadlines(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, searchingQuery: String, pageSize: Int): Resource<ApiResponse>{
        return newsRepository.getSearchedNews(country, searchingQuery, pageSize)
    }
}