package ru.kamishnikov.testapprss.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.kamishnikov.testapprss.App
import ru.kamishnikov.testapprss.R
import ru.kamishnikov.testapprss.data.FormatConverter
import ru.kamishnikov.testapprss.data.db.NewsDao
import ru.kamishnikov.testapprss.data.db.NewsEntity

class MainActivity : AppCompatActivity() {

    private val mAdapter = NewsAdapter()
    private var news: List<NewsEntity>? = null
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        news_recycler.layoutManager = LinearLayoutManager(this)

        getDataFromInternet()

        swipe_refresh.setOnRefreshListener {
            getDataFromInternet()
        }


    }

    private fun getDataFromInternet() {
        if(isOnline()) {
            disposable.add(App.restApi.getRss("rss")
                .map { FormatConverter.map(it) }
                .subscribeOn(Schedulers.io())
                .doOnSuccess { newsItem ->
                    getNewsDao().deleteAllNews()
                    newsItem.let {
                        getNewsDao().insertAllNews(it)
                        news = getNewsDao().getAllNews()
                    }
                    swipe_refresh.isRefreshing = false
                }
                .onErrorReturn {
                    Log.d("TAG", it.message)
                    news = getNewsDao().getAllNews()
                    swipe_refresh.isRefreshing = false
                    news
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    news?.let {
                        showNews(it)
                    }
                }, {
                    Log.d("TAG", it.message)
                })
            )
            swipe_refresh.isRefreshing = false

        }
        else{
            Toast.makeText(this,"Интернет-подключение отсутствует",Toast.LENGTH_LONG).show()
            swipe_refresh.isRefreshing = false
        }
    }

    private fun showNews(it: List<NewsEntity>?) {
        news_recycler.adapter = mAdapter
        mAdapter.addData(it as MutableList<NewsEntity>)
        swipe_refresh.isRefreshing = false
    }


    private fun isOnline(): Boolean {
        val connectivityManager = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    private fun getNewsDao(): NewsDao {
        return App.database.newsDao
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
