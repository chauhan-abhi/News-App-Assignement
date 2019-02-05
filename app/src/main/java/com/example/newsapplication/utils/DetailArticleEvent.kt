package com.example.newsapplication.utils

import com.example.newsapplication.data.db.ArticleEntity

class DetailArticleEvent(articleEntity: ArticleEntity) {
    val articleEntity: ArticleEntity = articleEntity
}
