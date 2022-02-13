package com.example.dailytopnews.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.dailytopnews.data.model.ApiResponse
import com.example.dailytopnews.data.model.Article
import com.example.dailytopnews.domain.usecase.*
import com.example.dailytopnews.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.Serializable
import java.lang.Exception

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlines: UseCaseGetNewsHeadlines,
    private val getSearchedNewsHeadlines: UseCaseGetSearchedNewsHeadlines,
    private val saveArticles: UseCaseSaveNews,
    private val getSavedNews: UseCaseGetSavedNews,
    private val deleteSavedNews: UseCaseDeleteSavedNews,
): AndroidViewModel(app) {
    var getHeadLines: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    var getSearchedHeadlines: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, pageSize: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                getHeadLines.postValue(Resource.Loading())
                val apiResult = getNewsHeadlines.execute(country, pageSize)
                getHeadLines.postValue(apiResult)
            } else {
                getHeadLines.postValue(Resource.Failure("InternetError"))
            }
        }catch (e: Exception){
            getHeadLines.postValue(Resource.Failure(e.message.toString()))
        }
    }

    fun getSearchedNewsHeadLines(country: String, searchQuery: String, pageSize: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)) {
                getSearchedHeadlines.postValue(Resource.Loading())
                val apiResult = getSearchedNewsHeadlines.execute(country, searchQuery, pageSize)
                getSearchedHeadlines.postValue(apiResult)
            } else {
                getSearchedHeadlines.postValue(Resource.Failure("Internet is not available"))
            }
        }catch (e: Exception){
            getSearchedHeadlines.postValue(Resource.Failure(e.message.toString()))
        }
    }

    fun saveNews(article: Article) = viewModelScope.launch{
        saveArticles.execute(article)
    }

    fun getSavedNews() = liveData<List<Article>> {
        getSavedNews.execute().collect{
            emit(it)
        }
    }

    fun deleteSavedNews(article: Article) = viewModelScope.launch{
        deleteSavedNews.execute(article)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
        return result
    }


    private val _mutableSelectedArticle = MutableLiveData<Article>()
    val mutableSelectedArticle: LiveData<Article>
    get() = _mutableSelectedArticle
    private var _fragmentSelector: Int = 0
    val fragmentSelector: Int
    get() = _fragmentSelector

    fun selectedArticle(article: Article){
         _mutableSelectedArticle.value = article
     }

    fun fragmentSelector(id: Int){
        _fragmentSelector = id
    }
}