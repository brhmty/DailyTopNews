package com.brhmty.dailytopnews.presentation.di

import com.brhmty.dailytopnews.presentation.adapter.NewsAdapter
import com.brhmty.dailytopnews.presentation.adapter.SavedNewsAdapter
import com.brhmty.dailytopnews.presentation.adapter.SearchedNewsAdapter
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