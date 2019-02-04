package com.example.newsapplication.di

import com.example.newsapplication.view.ui.MainActivity
import com.example.newsapplication.NewsApp
import com.example.newsapplication.view.viewmodel.NewsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: NewsApp)
    fun inject(activity: MainActivity)
    fun inject(newsViewModel: NewsViewModel)

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule):Builder

        fun build(): AppComponent
    }
}