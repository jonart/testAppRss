package ru.kamishnikov.testapprss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.restApi.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                it.channel?.itemList?.get(0)?.title
                 }
            .onErrorReturn {
                Log.e("RX", it.toString())
                error("errorMessage")
            }
            .subscribe(Consumer {
                Log.d("RX", "onSuccess")
            }, Consumer { Log.e("RX", it.toString()) })
    }
}
