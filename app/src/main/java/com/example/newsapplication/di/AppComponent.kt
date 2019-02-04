package com.example.newsapplication.di

import android.app.Application
import com.example.newsapplication.NewsApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetModule::class
))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application):Builder

        fun build(): AppComponent
    }

    fun inject(app: NewsApp)
}