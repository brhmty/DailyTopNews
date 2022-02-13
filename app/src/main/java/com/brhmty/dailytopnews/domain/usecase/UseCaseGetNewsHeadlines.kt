package com.brhmty.dailytopnews.domain.usecase

import com.brhmty.dailytopnews.data.model.ApiResponse
import com.brhmty.dailytopnews.data.repository.NewsRepository
import com.brhmty.dailytopnews.domain.util.Resource

class UseCaseGetNewsHeadlines(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, pageSize: Int): Resource<ApiResponse>{
        return newsRepository.getNewsHeadlines(country, pageSize)
    }
}