package com.example.newsapplication.data

import com.example.newsapplication.data.model.responsemodels.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    fun getTopHeadlines(@Query("country") country: String,
                        @Query("category") category: String): Single<NewsResponse>

    @GET("everything")
    fun getEverything(@Query("q") query: String,
                      @Query("sortBy") sortBy: String): Single<NewsResponse>
}