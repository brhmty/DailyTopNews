package com.brhmty.dailytopnews.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brhmty.dailytopnews.domain.usecase.*

class NewsViewModelFactory(
    private val app: Application,
    private val getNewsHeadlines: UseCaseGetNewsHeadlines,
    private val getSearchedNewsHeadlines: UseCaseGetSearchedNewsHeadlines,
    private val saveNews: UseCaseSaveNews,
    private val getSavedNews: UseCaseGetSavedNews,
    private val deleteSavedNews: UseCaseDeleteSavedNews,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, getNewsHeadlines, getSearchedNewsHeadlines, saveNews, getSavedNews, deleteSavedNews) as T
    }
}