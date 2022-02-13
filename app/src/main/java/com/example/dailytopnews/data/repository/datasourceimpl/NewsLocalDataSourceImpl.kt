package com.example.dailytopnews.data.repository.datasourceimpl

import com.example.dailytopnews.data.db.ArticleDao
import com.example.dailytopnews.data.model.Article
import com.example.dailytopnews.data.repository.datasource.NewsLocalDataSource
import com.example.dailytopnews.data.repository.datasource.NewsRemoteDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDao: ArticleDao,
): NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDao.insert(article)
    }

    override fun getArticleFromDB(): Flow<List<Article>> {
        return articleDao.getSavedArticle()
    }

    override suspend fun deleteArticleFromDB(article: Article) {
        return articleDao.deleteSavedArticle(article)
    }
}