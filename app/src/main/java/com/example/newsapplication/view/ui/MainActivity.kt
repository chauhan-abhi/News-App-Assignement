package com.example.newsapplication.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
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
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("title", event.articleEntity.title)
        intent.putExtra("desc", event.articleEntity.description)
        intent.putExtra("imgUrl", event.articleEntity.image)
        intent.putExtra("url", event.articleEntity.url)
        startActivity(intent)
    }
}
