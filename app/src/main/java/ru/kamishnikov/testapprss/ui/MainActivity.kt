package ru.kamishnikov.testapprss.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.kamishnikov.testapprss.App
import ru.kamishnikov.testapprss.R
import ru.kamishnikov.testapprss.data.FormatConverter
import ru.kamishnikov.testapprss.data.db.NewsDao
import ru.kamishnikov.testapprss.data.db.NewsEntity
import java.util.*

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

    private fun getDataFromInternet() = if(isOnline()) {
        disposable.add(App.restApi.getRss("rss")
            .map { FormatConverter.map(it) }
            .subscribeOn(Schedulers.io())
            .doOnSuccess { newsItem ->
                getNewsDao().deleteAllNews()
                newsItem.let {
                    getNewsDao().insertAllNews(it)
                    news = getNewsDao().news
                }
            }
            .onErrorReturn {
                Log.d("TAG", it.message)
                news = getNewsDao().news
                news
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                news?.let {
                    showNews()
                }
            }, {
                Log.d("TAG", it.message)
            })
        )
        swipe_refresh.isRefreshing = false

    }
    else{
        Toast.makeText(this,"Интернет-подключение отсутствует",Toast.LENGTH_LONG).show()
        disposable.add(getNewsDao().getAllNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                news = it
                showNews()
            }, {Log.d("TAG", it.message)}
            )
        )
        swipe_refresh.isRefreshing = false
    }

    private fun showNews() {
        news_recycler.adapter = mAdapter
        mAdapter.addData(news as MutableList<NewsEntity>)
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
