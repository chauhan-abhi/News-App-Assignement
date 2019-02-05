package com.example.newsapplication.data.repository

import android.widget.Toast
import com.example.newsapplication.data.db.ArticleDao
import com.example.newsapplication.data.db.ArticleEntity
import com.example.newsapplication.data.network.NewsApiService
import com.example.newsapplication.data.network.model.Article
import com.example.newsapplication.data.network.model.responsemodels.NewsResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val articleDao: ArticleDao
) {

    fun getNewsFromDb(): Observable<List<ArticleEntity>> {
        return articleDao.getArticles().filter { it.isNotEmpty() }
            .toObservable()
            .doOnNext {
                Timber.d("Dispatching ${it.size} articles from DB...")

            }
    }

    fun getNewsFromApi(country: String, category: String): Observable<NewsResponse> {
        return newsApiService.getTopHeadlines(country = country, category = category)
            //.filter { it.status == "ok" }
            .doOnNext {
                Timber.d("Dispatching ${it.articles.size} articles from API...")
                val list: ArrayList<ArticleEntity> = ArrayList()
                for (article in it.articles) {
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
                storeNewsInDb(list)
            }
    }

    fun storeNewsInDb(list: List<ArticleEntity>) {

        Observable.fromCallable { articleDao.insertAll(list) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Timber.d("Inserted ${list.size} articles from API in DB...")
            }
    }
}
