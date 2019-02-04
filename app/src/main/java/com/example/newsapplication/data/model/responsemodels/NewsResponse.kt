package com.example.newsapplication.data.model.responsemodels

import com.example.newsapplication.data.model.Article

class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)