package com.example.newsapplication.view.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.example.newsapplication.base.BaseViewModel
import com.example.newsapplication.data.repository.NewsRepository
import com.example.newsapplication.di.Injector
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class NewsViewModel : BaseViewModel() {

    @Inject
    lateinit var newsRepository: NewsRepository

    private lateinit var networkSubscription: Disposable
    private lateinit var dbSubscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val buttonVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    //val postListAdapter: PostListAdapter = PostListAdapter(ArrayList())
//    val errorClickListener = View.OnClickListener {
//        loadPosts()
//        buttonVisibility.value = View.GONE
//    }

    init {
        Injector.appComponent.inject(this)
    }



}