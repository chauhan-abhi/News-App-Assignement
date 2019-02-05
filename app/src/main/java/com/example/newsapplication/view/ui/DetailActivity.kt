package com.example.newsapplication.view.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.newsapplication.R
import com.example.newsapplication.utils.loadImg




class DetailActivity : AppCompatActivity() {

    var title: String = ""
    var desc: String = ""
    var imageUrl: String =""
    var url: String = ""
    lateinit var button: Button
    lateinit var contentHeadline: TextView
    lateinit var contentDesc: TextView
    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setViews()
        createToolbar()
        floatingButton()
        fullLinkButton()
    }

    private fun fullLinkButton() {
        button  = findViewById(R.id.article_button)
        button.setOnClickListener {
            openWebView()
        }
    }

    private fun openWebView() {

    }

    private fun setViews() {
        title =  intent.getStringExtra("title")
        desc = intent.getStringExtra("desc")
        imageUrl = intent.getStringExtra("imgUrl")
        url = intent.getStringExtra("url")
        contentHeadline = findViewById(R.id.content_Headline)
        contentHeadline.text = title
        contentDesc = findViewById(R.id.content_Description)
        contentDesc.text = desc
        image = findViewById(R.id.collapsingImage)
        image.loadImg(imageUrl)
    }

    private fun createToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_article)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun floatingButton() {
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this news! Send from MyTimes App\n" + Uri.parse(url))
            startActivity(Intent.createChooser(shareIntent, "Share with"))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }
}
