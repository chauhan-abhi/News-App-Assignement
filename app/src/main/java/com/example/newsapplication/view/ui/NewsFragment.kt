package com.example.newsapplication.view.ui


import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import com.example.newsapplication.R
import com.example.newsapplication.data.db.ArticleEntity
import com.example.newsapplication.data.network.model.Article
import com.example.newsapplication.data.repository.NewsRepository
import com.example.newsapplication.di.Injector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.TextView
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.newsapplication.utils.getCountryCode


class NewsFragment : Fragment() {

    @Inject
    lateinit var newsRepository: NewsRepository

    private lateinit var rvNews: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchEditText: EditText
    private lateinit var progressDialog: ProgressDialog
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
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        rvNews = view.findViewById(R.id.upcoming_news)
        initSwipeRefresh(view)
        searchEditText = view.findViewById(com.example.newsapplication.R.id.search_editext)
        return view
    }

    private fun initSwipeRefresh(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout)
        swipeRefreshLayout.setOnRefreshListener {
            loadNewsFromApi()
        }

        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        );

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        rvNews.layoutManager = llm
        newsList = ArrayList()
        newsListAdapter = NewsListAdapter(context, newsList)
        rvNews.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rvNews.adapter = newsListAdapter
        searchEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == 3) {
                    val mgr = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    mgr.hideSoftInputFromWindow(v?.windowToken, 0)
                    searchNewsFromQuery(searchEditText.text.toString())
                    return true
                }
                return false
            }
        })
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
        var countryCode = getCountryCode(context!!)
        if (countryCode.isNullOrEmpty())
            countryCode = context!!.resources.configuration.locale.country
        networkSubscription =
                newsRepository.getNewsFromApi(countryCode, "")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { showLoading() }
                    .doOnTerminate { hideLoading() }
                    .subscribe(
                        { newsResponse -> onNewsSuccess(newsResponse.articles) },
                        { loadNewsFromDb() }
                    )
        swipeRefreshLayout.isRefreshing = false
    }

    private fun searchNewsFromQuery(query: String) {
        networkSubscription =
                newsRepository.getNewsFromSearch(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { showLoading() }
                    .doOnTerminate { hideLoading() }
                    .subscribe(
                        { newsResponse -> onNewsSuccess(newsResponse.articles) },
                        { noNewsFound() }
                    )

    }

    private fun noNewsFound() {
        Toast.makeText(context, "No news found", Toast.LENGTH_SHORT)
    }

    private fun hideLoading() {
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    private fun showLoading() {
        progressDialog = ProgressDialog(context)
        val inflater = LayoutInflater.from(context)
        progressDialog.getWindow()!!.requestFeature(1)
        progressDialog.getWindow()!!.setBackgroundDrawableResource(17170445)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        progressDialog.setContentView(inflater.inflate(R.layout.progress, null as ViewGroup?))
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
        newsListAdapter.setData(newsList)
    }

    private fun onErrorFetchingFromDb() {
        //errorMessage.value = "Error"
    }

}
