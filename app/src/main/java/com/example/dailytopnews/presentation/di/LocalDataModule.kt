package com.example.dailytopnews.presentation.di

import com.example.dailytopnews.data.db.ArticleDao
import com.example.dailytopnews.data.repository.datasource.NewsLocalDataSource
import com.example.dailytopnews.data.repository.datasourceimpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun providesLocalDataSource(articleDao: ArticleDao): NewsLocalDataSource{
        return NewsLocalDataSourceImpl(articleDao)
    }
}