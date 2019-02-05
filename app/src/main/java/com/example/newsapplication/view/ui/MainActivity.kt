package com.example.newsapplication.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.newsapplication.R
import com.example.newsapplication.base.BaseActivity
import com.example.newsapplication.base.TransactionFragmentHelper
import com.example.newsapplication.utils.DetailArticleEvent
import com.example.newsapplication.view.viewmodel.NewsListViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity() {

    //private lateinit var viewModel: NewsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUi(R.layout.activity_frame_layout, R.id.frame_layout)
        val newsFragment = NewsFragment()
        TransactionFragmentHelper.addFragment(
            R.id.container,
            newsFragment,
            "News Fragment",
            supportFragmentManager
        )
    }

    override fun setUpComponent() {

    }

    override fun setUpViewHolder(view: View?) {
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openDetailArticle(event: DetailArticleEvent) {


    }
/*
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
