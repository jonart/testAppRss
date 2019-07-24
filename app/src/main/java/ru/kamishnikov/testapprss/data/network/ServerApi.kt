package ru.kamishnikov.testapprss.data.network

import io.reactivex.Single
import me.toptas.rssconverter.RssFeed
import retrofit2.http.GET
import retrofit2.http.Url

interface ServerApi {
    @GET
    fun getRss(@Url url: String): Single<RssFeed>
}