package com.example.newsapplication.view.ui

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.base.BaseRecyclerViewAdapter
import com.example.newsapplication.data.db.ArticleEntity
import com.example.newsapplication.utils.DetailArticleEvent
import com.example.newsapplication.utils.getElapsedTimeString
import com.example.newsapplication.utils.loadImg
import org.greenrobot.eventbus.EventBus

class NewsListAdapter(newsList: List<ArticleEntity>):
BaseRecyclerViewAdapter<ArticleEntity, NewsListAdapter.ViewHolder>(newsList){

    private lateinit var inflater: LayoutInflater


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListAdapter.ViewHolder {
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList?.get(position)!!)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var title: TextView
        private var author: TextView
        private var time: TextView
        private var newsImage: ImageView
        private var articleCardView: CardView

        init {
            title = view.findViewById(R.id.title_textview)
            author = view.findViewById(R.id.source_textview)
            time = view.findViewById(R.id.time_textView)
            newsImage = view.findViewById(R.id.imageView)
            articleCardView = view.findViewById(R.id.articleCardView)
        }

        fun bind(article: ArticleEntity) {
            title.text = article.title
            author.text = article.sourceName
            time.text = article.publishedAt.getElapsedTimeString()
            newsImage.loadImg(article.image)
            articleCardView.setOnClickListener {
                EventBus.getDefault().post(DetailArticleEvent(article))
            }
        }
    }
}