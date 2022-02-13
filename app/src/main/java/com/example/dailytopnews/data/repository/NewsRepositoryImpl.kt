package com.example.dailytopnews.data.repository

import com.example.dailytopnews.data.model.ApiResponse
import com.example.dailytopnews.data.model.Article
import com.example.dailytopnews.data.repository.datasource.NewsLocalDataSource
import com.example.dailytopnews.data.repository.datasource.NewsRemoteDataSource
import com.example.dailytopnews.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource,
): NewsRepository {
    override suspend fun getNewsHeadlines(country: String, pageSize:Int): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, pageSize))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchingQuery: String,
        pageSize: Int,
    ): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedHeadlines(country, searchingQuery, pageSize))
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteSavedNews(article: Article) {
        newsLocalDataSource.deleteArticleFromDB(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getArticleFromDB()
    }

    private fun responseToResource(response: Response<ApiResponse>): Resource<ApiResponse>{
        if(response.isSuccessful)
            response.body()?.let { 
                apiResponse -> return Resource.Success(apiResponse)
            }
       return Resource.Failure(response.message())
    }
}