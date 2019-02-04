package com.example.newsapplication.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.di.Injector
import com.example.newsapplication.utils.ViewModelFactory
import com.example.newsapplication.view.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.appComponent.inject(this)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.newsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(NewsViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel
        //loadfromDb()
    }

    private fun hideError() {
    }

    private fun showError(errorMessage: Any) {

    }
/*
    override fun onStart() {
        super.onStart()
        loadArticlesfromApi()
    }

    private fun loadfromDb() {
        dbSubscription = newsRepository.getNewsFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {}
            .doOnTerminate {}
            .subscribe(
                { postList ->
                    onPostsFetchedFromDb(postList)
                }, {
                    //onErrorFetchingFromDb()
                }
            )
    }

    private fun onPostsFetchedFromDb(postList: List<ArticleEntity>?) {

    }

    private fun loadArticlesfromApi() {
        networkSubscription = newsRepository.getNewsFromApi("us", "business")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doOnTerminate { hideLoading() }
            .subscribe(
                { newsResponse -> onPostsSuccess(newsResponse.articles) },
                { onPostsError() }
            )
    }

    private fun hideLoading() {
        Log.d("ss", "ss")
    }

    private fun showLoading() {
        Log.d("ss", "ss")
    }

    private fun onPostsError() {
        Log.d("ss", "ss")
    }

    private fun onPostsSuccess(postList: List<Article?>) {
        Log.d("ss", "ss" + postList.toString())
    }*/
}
