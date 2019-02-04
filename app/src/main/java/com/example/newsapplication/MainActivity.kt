package com.example.newsapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapplication.data.NewsApiService
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var newsApiService: NewsApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
