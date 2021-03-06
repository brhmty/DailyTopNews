package com.brhmty.dailytopnews.presentation.di

import com.brhmty.dailytopnews.data.api.NewsApiService
import com.brhmty.dailytopnews.data.repository.datasource.NewsRemoteDataSource
import com.brhmty.dailytopnews.data.repository.datasourceimpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    @Singleton
    @Provides
    fun providesRemoteDataSource(newsApiService: NewsApiService): NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsApiService)
    }
}