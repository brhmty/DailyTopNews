package com.example.dailytopnews.domain.usecase

import com.example.dailytopnews.data.model.ApiResponse
import com.example.dailytopnews.data.repository.NewsRepository
import com.example.dailytopnews.domain.util.Resource

class UseCaseGetNewsHeadlines(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, pageSize: Int): Resource<ApiResponse>{
        return newsRepository.getNewsHeadlines(country, pageSize)
    }
}