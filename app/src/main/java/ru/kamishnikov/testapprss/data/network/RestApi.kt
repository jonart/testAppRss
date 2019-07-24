package ru.kamishnikov.testapprss.data.network

import io.reactivex.schedulers.Schedulers
import me.toptas.rssconverter.RssConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

class RestApi private constructor() {
    val api: ServerApi
    private val ENDPOINT = "https://lenta.ru/"


    init {
        val okHttpClient = buildOkHttpClient()
        val retrofit = buildRetrofit(okHttpClient)
        api = retrofit.create(ServerApi::class.java)
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(2, TimeUnit.SECONDS)
            .build()
    }


    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .client(client)
            .addConverterFactory(RssConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    companion object {

        private var restApi: RestApi? = null


        val instance: RestApi
            @Synchronized get() {
                if (restApi == null) {
                    restApi =
                        RestApi()
                }
                return restApi as RestApi
            }
    }
}