package com.example.dailytopnews.presentation.di

import com.example.dailytopnews.data.repository.NewsRepositoryImpl
import com.example.dailytopnews.data.repository.datasource.NewsLocalDataSource
import com.example.dailytopnews.data.repository.datasource.NewsRemoteDataSource
import com.example.dailytopnews.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun providesNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource,
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }
}