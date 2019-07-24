package ru.kamishnikov.testapprss

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var restApi: ServerApi
//        lateinit var database: NewsDatabase
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        restApi = RestApi.instance.api
//        database = Room.databaseBuilder(applicationContext, NewsDatabase::class.java, "news_database.db")
//            .fallbackToDestructiveMigration()
//            .build()
        context = applicationContext
    }

}