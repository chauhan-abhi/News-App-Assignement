package com.example.newsapplication.view.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.newsapplication.R
import android.support.v7.widget.Toolbar
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.graphics.Bitmap
import android.webkit.WebViewClient
import android.annotation.SuppressLint
import android.content.Context
import android.view.MenuItem
import android.view.View
import android.view.MotionEvent
import com.example.newsapplication.constants.Constants

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var url: String
    private lateinit var title: TextView
    private var m_downX: Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        getParams()
        createToolbar()
        webView = findViewById(R.id.webView_article)
        progressBar = findViewById(R.id.progressBar)
        if (savedInstanceState == null) {
            webView.loadUrl(url)
        }
    }

    private fun createToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_web_view)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = findViewById(R.id.toolbar_title_web_view)
        //mTitle.setTypeface(montserrat_regular)
        title.text = url

    }

    private fun getParams() {
        url = intent.getStringExtra("url")

    }


    private inner class MyWebChromeClient(internal var context: Context) : WebChromeClient()

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.getItemId()) {

            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putString(Constants.TITLE_WEBVIEW_KEY, url)
        webView.saveState(bundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            createToolbar()
            webView.restoreState(savedInstanceState)
            title.text = savedInstanceState.getString(Constants.TITLE_WEBVIEW_KEY)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(com.example.newsapplication.R.anim.enter_from_left, R.anim.exit_to_right)
    }
}
