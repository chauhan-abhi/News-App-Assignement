package com.example.newsapplication.di

import com.example.newsapplication.view.ui.MainActivity
import com.example.newsapplication.NewsApp
import com.example.newsapplication.view.ui.NewsFragment
import com.example.newsapplication.view.viewmodel.NewsListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: NewsApp)
    fun inject(activity: MainActivity)
    fun inject(newsViewModel: NewsListViewModel)
    fun inject(newsFragment: NewsFragment)

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule):Builder

        fun build(): AppComponent
    }
}