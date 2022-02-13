package com.example.dailytopnews.presentation.di

import com.example.dailytopnews.data.repository.NewsRepository
import com.example.dailytopnews.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun providesGetNewsHeadlines(newsRepository: NewsRepository): UseCaseGetNewsHeadlines{
        return UseCaseGetNewsHeadlines(newsRepository)
    }

    @Singleton
    @Provides
    fun providesGetSearchedNews(newsRepository: NewsRepository): UseCaseGetSearchedNewsHeadlines{
        return UseCaseGetSearchedNewsHeadlines(newsRepository)
    }

    @Singleton
    @Provides
    fun providesSaveNews(newsRepository: NewsRepository): UseCaseSaveNews{
        return UseCaseSaveNews(newsRepository)
    }

    @Singleton
    @Provides
    fun providesGetSavedArticles(newsRepository: NewsRepository): UseCaseGetSavedNews{
        return UseCaseGetSavedNews(newsRepository)
    }

    @Singleton
    @Provides
    fun providesDeleteSavedNews(newsRepository: NewsRepository): UseCaseDeleteSavedNews{
        return UseCaseDeleteSavedNews(newsRepository)
    }
}
