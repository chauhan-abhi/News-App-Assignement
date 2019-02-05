package com.example.newsapplication.view.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.newsapplication.data.db.ArticleEntity

class NewsViewModel: ViewModel() {

    private val title = MutableLiveData<String>()
    private val author = MutableLiveData<String>()

    fun bind(article: ArticleEntity) {
        title.value = article.title
        author.value = article.author
    }

    fun getTitle(): MutableLiveData<String> {
        return title
    }
    fun getAuthor(): MutableLiveData<String> {
        return author
    }


}
