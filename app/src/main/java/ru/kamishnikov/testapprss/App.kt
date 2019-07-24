package ru.kamishnikov.testapprss

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.facebook.stetho.Stetho
import ru.kamishnikov.testapprss.data.db.NewsDatabase
import ru.kamishnikov.testapprss.data.network.RestApi
import ru.kamishnikov.testapprss.data.network.ServerApi

class App : Application() {

    companion object {
        lateinit var restApi: ServerApi
        lateinit var database: NewsDatabase
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        restApi = RestApi.instance.api
        context = applicationContext
        database = Room.databaseBuilder(applicationContext, NewsDatabase::class.java, "news_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}