package com.example.newsapplication.di

import com.example.newsapplication.constants.Constants
import com.example.newsapplication.constants.Constants.API_KEY
import com.example.newsapplication.data.NewsApiService
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetModule {

    @Provides
    fun provideNewsApi(retrofit: Retrofit) = retrofit.create(NewsApiService::class.java)

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) =
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("X-Api-Key", API_KEY).build()
                chain.proceed(request)
            }
            .build()
    }
}
