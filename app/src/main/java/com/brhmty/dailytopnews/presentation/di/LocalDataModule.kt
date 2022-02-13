package com.brhmty.dailytopnews.presentation.di

import com.brhmty.dailytopnews.data.db.ArticleDao
import com.brhmty.dailytopnews.data.repository.datasource.NewsLocalDataSource
import com.brhmty.dailytopnews.data.repository.datasourceimpl.NewsLocalDataSourceImpl
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