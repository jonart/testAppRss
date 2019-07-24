package ru.kamishnikov.testapprss.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kamishnikov.testapprss.R
import ru.kamishnikov.testapprss.data.db.NewsEntity

class NewsAdapter() : RecyclerView.Adapter<NewsHolder>() {

    private val news: MutableList<NewsEntity> = mutableListOf()

    fun addData(items: MutableList<NewsEntity>) {
        news.clear()
        news.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsHolder {
        return NewsHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item, viewGroup, false)
        )
    }

    override fun onBindViewHolder(newsHolder: NewsHolder, i: Int) {
        newsHolder.bind(news[i])
    }

    override fun getItemCount(): Int {
        return news.size
    }

}