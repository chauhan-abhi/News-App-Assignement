package com.example.newsapplication.data.network

import com.example.newsapplication.data.network.model.responsemodels.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    fun getTopHeadlines(@Query("country") country: String,
                        @Query("category") category: String): Observable<NewsResponse>

    @GET("everything")
    fun getEverything(@Query("q") query: String ): Observable<NewsResponse>
}