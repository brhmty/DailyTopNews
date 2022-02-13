package com.brhmty.dailytopnews.domain.usecase

import com.brhmty.dailytopnews.data.model.Article
import com.brhmty.dailytopnews.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class UseCaseGetSavedNews(private val newsRepository: NewsRepository) {
   fun execute(): Flow<List<Article>>{
       return newsRepository.getSavedNews()
   }
}