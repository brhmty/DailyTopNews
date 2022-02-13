package com.example.dailytopnews.data.db

import androidx.room.*
import com.example.dailytopnews.data.model.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles_table")
    fun getSavedArticle(): Flow<List<Article>>

    @Delete
    suspend fun deleteSavedArticle(article: Article)
}