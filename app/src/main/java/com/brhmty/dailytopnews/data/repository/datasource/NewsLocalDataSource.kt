package com.brhmty.dailytopnews.data.repository.datasource

import com.brhmty.dailytopnews.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)
    fun getArticleFromDB(): Flow<List<Article>>
    suspend fun deleteArticleFromDB(article: Article)
}