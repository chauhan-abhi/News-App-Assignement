package com.example.newsapplication.view.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import android.view.View
import com.example.newsapplication.R
import com.example.newsapplication.base.BaseViewModel
import com.example.newsapplication.data.db.ArticleEntity
import com.example.newsapplication.data.network.model.Article
import com.example.newsapplication.data.repository.NewsRepository
import com.example.newsapplication.di.Injector
import com.example.newsapplication.view.ui.NewsListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel : BaseViewModel() {

    @Inject
    lateinit var newsRepository: NewsRepository

    private lateinit var networkSubscription: Disposable
    private lateinit var dbSubscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    //val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val newsListAdapter: NewsListAdapter = NewsListAdapter(ArrayList())

    val errorClickListener = View.OnClickListener {
        loadNews()
    }

    init {
        Injector.appComponent.inject(this)
    }

    private fun loadNews() {

    }

    fun loadNewsFromDb() {
        dbSubscription = newsRepository.getNewsFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {}
            .doOnTerminate {}
            .subscribe(
                { newsList ->
                    onNewsFetched(newsList)
                }, {
                    onErrorFetchingFromDb()
                }
            )
    }



    fun loadNewsFromApi() {
        networkSubscription = newsRepository.getNewsFromApi("us", "business")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doOnTerminate { hideLoading() }
            .subscribe(
                { newsResponse -> onNewsSuccess(newsResponse.articles) },
                { loadNewsFromDb() }
            )
    }

    private fun hideLoading() {
        loadingVisibility.value = View.GONE
    }

    private fun showLoading() {
        loadingVisibility.value = View.VISIBLE
        //errorMessage.value = null
    }

    private fun onNewsSuccess(newsList: List<Article>) {
        val list: ArrayList<ArticleEntity> = ArrayList()
        for (article in newsList) {
            val articleEntity = ArticleEntity(
                0,
                article.title ?: "",
                article.author ?: "",
                article.source.id ?: "",
                article.source.name ?: "",
                article.description ?: "",
                article.url ?: "",
                article.urlToImage ?: "",
                article.publishedAt ?: ""
            )
            list.add(articleEntity)
        }
        onNewsFetched(list)
    }

    private fun onNewsFetched(newsList: List<ArticleEntity>) {
        //setadapter
        newsListAdapter.setData(newsList)
    }

    private fun onErrorFetchingFromDb() {
        //errorMessage.value = "Error"
    }

    override fun onCleared() {
        super.onCleared()
        networkSubscription.dispose()
        dbSubscription.dispose()
    }


}