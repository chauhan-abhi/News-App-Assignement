package com.example.newsapplication.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import com.example.newsapplication.view.viewmodel.NewsListViewModel

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            return NewsListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}