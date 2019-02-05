package com.example.newsapplication.view.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.example.newsapplication.R
import com.example.newsapplication.base.BaseActivity
import com.example.newsapplication.base.TransactionFragmentHelper
import com.example.newsapplication.utils.DetailArticleEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode



class MainActivity : BaseActivity() {

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

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
