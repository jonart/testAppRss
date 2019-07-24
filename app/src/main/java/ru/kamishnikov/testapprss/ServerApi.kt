package ru.kamishnikov.testapprss

import io.reactivex.Single
import retrofit2.http.GET
import ru.kamishnikov.testapprss.model.RssFeed

interface ServerApi {
    @GET("rss")
    fun getNews(): Single<RssFeed>
}