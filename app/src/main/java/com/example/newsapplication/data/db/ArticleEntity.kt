package com.example.newsapplication.data.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index

@Entity(
    tableName = "article",
    indices = [
        (Index("url"))],
    primaryKeys = ["url"]
)
data class ArticleEntity(
    val id: Int,
    val title: String,
    val author: String,
    val sourceId: String,
    val sourceName: String,
    val url: String,
    val image: String,
    val publishedAt: String
)