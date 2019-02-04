package com.example.newsapplication.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: ArticleEntity)

    @Query("SELECT * FROM article")
    fun getArticles(): Single<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<ArticleEntity>)


}