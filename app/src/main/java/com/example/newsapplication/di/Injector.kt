package com.example.newsapplication.di

import com.example.newsapplication.NewsApp

object Injector {
    lateinit var appComponent: AppComponent

    fun init(app: NewsApp) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
        appComponent.inject(app)
    }

}
