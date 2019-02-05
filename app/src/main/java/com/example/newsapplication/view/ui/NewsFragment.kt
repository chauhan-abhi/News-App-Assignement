package com.example.newsapplication.view.ui


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.newsapplication.R
import com.example.newsapplication.data.db.ArticleEntity
import com.example.newsapplication.data.network.NewsApiService
import com.example.newsapplication.data.network.model.Article
import com.example.newsapplication.data.repository.NewsRepository
import com.example.newsapplication.di.Injector
import com.example.newsapplication.utils.DetailArticleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class NewsFragment : Fragment() {

    @Inject
    lateinit var newsRepository: NewsRepository

    private lateinit var rvNews: RecyclerView
    private lateinit var newsList: List<ArticleEntity>
    private lateinit var newsListAdapter: NewsListAdapter
    private lateinit var networkSubscription: Disposable
    private lateinit var dbSubscription: Disposable

    init {
        Injector.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_news, container, false)
        rvNews = view.findViewById(R.id.upcoming_news)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        rvNews.layoutManager = llm
        newsList = ArrayList()
        newsListAdapter = NewsListAdapter(newsList)
        rvNews.adapter = newsListAdapter
    }

    override fun onStop() {
        super.onStop()
        networkSubscription.dispose()
        if (dbvisited)
            dbSubscription.dispose()
    }

    override fun onResume() {
        super.onResume()
        loadNewsFromApi()
    }


    private var dbvisited: Boolean = false

    private fun loadNewsFromDb() {

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
        dbvisited = true
    }

    private fun loadNewsFromApi() {
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
        //loadingVisibility.value = View.GONE
    }

    private fun showLoading() {
        //loadingVisibility.value = View.VISIBLE
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
                article.url ?: "",
                article.urlToImage ?: "",
                article.publishedAt ?: ""
            )
            list.add(articleEntity)
        }
        onNewsFetched(list)
    }

    private fun onNewsFetched(newsList: List<ArticleEntity>) {
        newsListAdapter.setData(newsList)
    }

    private fun onErrorFetchingFromDb() {
        //errorMessage.value = "Error"
    }


}
