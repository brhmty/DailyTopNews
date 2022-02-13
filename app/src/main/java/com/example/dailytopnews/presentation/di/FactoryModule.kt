package com.example.dailytopnews.presentation.di

import android.app.Application
import com.example.dailytopnews.domain.usecase.*
import com.example.dailytopnews.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun providesNewsViewModelFactory(
        app: Application,
        getNewsHeadlines: UseCaseGetNewsHeadlines,
        getSearchedNewsHeadlines: UseCaseGetSearchedNewsHeadlines,
        saveNews: UseCaseSaveNews,
        getSavedNews: UseCaseGetSavedNews,
        deleteSavedNews: UseCaseDeleteSavedNews,
    ): NewsViewModelFactory{
        return NewsViewModelFactory(app, getNewsHeadlines, getSearchedNewsHeadlines, saveNews, getSavedNews, deleteSavedNews)
    }
}