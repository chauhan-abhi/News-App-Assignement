package com.example.newsapplication.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(
    entities = [
        ArticleEntity::class
    ],
    version = 1,
    exportSchema = false)

abstract class NewsDb: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}