package com.example.newsapplication.data.network.model.responsemodels

import com.example.newsapplication.data.network.model.Article

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)