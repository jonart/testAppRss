package ru.kamishnikov.testapprss.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

import java.io.Serializable

@Root(name = "item", strict = false)
data class RssFeedItem(
    @Element(name = "title", required = true)
    val title: String,
    @Element(name = "description", required = true)
    val description: String,
    @Element(name = "pubDate", required = true)
    val publicationDate: String) {

    fun isEqualTo(o: RssFeedItem): Boolean {
        return o.title == title &&
            o.description == description &&
            o.publicationDate == publicationDate
    }

}