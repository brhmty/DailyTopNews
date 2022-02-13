package com.example.dailytopnews.domain.usecase

import com.example.dailytopnews.data.model.Article
import com.example.dailytopnews.data.repository.NewsRepository

class UseCaseSaveNews(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}