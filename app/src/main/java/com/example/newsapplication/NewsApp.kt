package com.example.newsapplication

import android.app.Activity
import android.app.Application
import com.example.newsapplication.di.AppComponent
import com.example.newsapplication.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class NewsApp: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun onCreate() {
        super.onCreate()
        initializeComponent()
        Stetho.initializeWithDefaults(this)
    }

    private fun initializeComponent() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}