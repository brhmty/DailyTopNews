package com.example.dailytopnews.domain.usecase

import com.example.dailytopnews.data.model.Article
import com.example.dailytopnews.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class UseCaseGetSavedNews(private val newsRepository: NewsRepository) {
   fun execute(): Flow<List<Article>>{
       return newsRepository.getSavedNews()
   }
}