package com.brhmty.dailytopnews.data.repository.datasourceimpl

import com.brhmty.dailytopnews.data.db.ArticleDao
import com.brhmty.dailytopnews.data.model.Article
import com.brhmty.dailytopnews.data.repository.datasource.NewsLocalDataSource
import com.brhmty.dailytopnews.data.repository.datasource.NewsRemoteDataSource
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