package com.brhmty.dailytopnews.domain.usecase

import com.brhmty.dailytopnews.data.model.Article
import com.brhmty.dailytopnews.data.repository.NewsRepository

class UseCaseSaveNews(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}