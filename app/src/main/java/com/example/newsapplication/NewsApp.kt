package com.example.newsapplication

import android.app.Activity
import android.app.Application
import com.example.newsapplication.di.AppComponent
import com.example.newsapplication.di.DaggerAppComponent
import com.example.newsapplication.di.Injector
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class NewsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
        Stetho.initializeWithDefaults(this)
    }
}