package ru.kamishnikov.testapprss.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kamishnikov.testapprss.R
import ru.kamishnikov.testapprss.data.db.NewsEntity

class NewsHolder(item: View) : RecyclerView.ViewHolder(item) {
    private val mTitle: TextView = item.findViewById(R.id.item_news_layout_recycler)

    fun bind(item: NewsEntity) {
        mTitle.text = item.title
    }
}