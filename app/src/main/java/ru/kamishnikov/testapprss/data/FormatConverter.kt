package ru.kamishnikov.testapprss.data

import me.toptas.rssconverter.RssFeed
import ru.kamishnikov.testapprss.data.db.NewsEntity
import java.util.*

class FormatConverter {

    companion object {
        fun map(dto: RssFeed): List<NewsEntity> {

            val items = ArrayList<NewsEntity>()

            for (i in dto.items?.indices!!) {
                val item = NewsEntity(dto.items?.get(i)?.title)
                items.add(item)
            }
            return Collections.unmodifiableList(items)
        }
    }


}