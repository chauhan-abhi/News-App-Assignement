package com.example.newsapplication.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.example.newsapplication.constants.Constants
import com.example.newsapplication.data.network.NewsApiService
import com.example.newsapplication.data.db.ArticleDao
import com.example.newsapplication.data.db.NewsDb
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule(internal var application: Application) {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    internal fun providesApplication(): Application = application

    @Singleton
    @Provides
    internal fun provideNewsApi(retrofit: Retrofit) = retrofit.create(NewsApiService::class.java)

    @Singleton
    @Provides
    internal fun providesRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("X-Api-Key", Constants.API_KEY).build()
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDb(app:Application): NewsDb {
        return Room
            .databaseBuilder(app, NewsDb::class.java, "articles.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(db: NewsDb) : ArticleDao = db.articleDao()
}