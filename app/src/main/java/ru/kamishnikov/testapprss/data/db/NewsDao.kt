package ru.kamishnikov.testapprss.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NewsDao {

    @get:Query("SELECT * FROM news")
    val news: MutableList<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(news: List<NewsEntity>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(newsEntity: NewsEntity)

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsById(id: Int): Single<NewsEntity>

    @Query("SELECT * FROM news")
    fun getAllNews(): Single<List<NewsEntity>>

    @Query("DELETE FROM news")
    fun deleteAllNews()

    @Query("DELETE FROM news WHERE id = :id")
    fun deleteById(id: Int)

}
