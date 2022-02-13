package com.brhmty.dailytopnews.presentation.di

import android.app.Application
import androidx.room.Room
import com.brhmty.dailytopnews.data.db.ArticleDao
import com.brhmty.dailytopnews.data.db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(app: Application): NewsDatabase{
        return Room.databaseBuilder(app, NewsDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesArticleDao(newsDatabase: NewsDatabase): ArticleDao{
        return newsDatabase.getArticleDao()
    }
}