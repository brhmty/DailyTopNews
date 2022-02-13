package com.example.dailytopnews.presentation.di

import com.example.dailytopnews.presentation.adapter.NewsAdapter
import com.example.dailytopnews.presentation.adapter.SavedNewsAdapter
import com.example.dailytopnews.presentation.adapter.SearchedNewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Singleton
    @Provides
    fun providesNewsAdapter(): NewsAdapter{
        return NewsAdapter()
    }

    @Singleton
    @Provides
    fun providesSearchedNewsAdapter(): SearchedNewsAdapter{
        return SearchedNewsAdapter()
    }

    @Singleton
    @Provides
    fun providesSavedNewsAdapter(): SavedNewsAdapter{
        return SavedNewsAdapter()
    }

}