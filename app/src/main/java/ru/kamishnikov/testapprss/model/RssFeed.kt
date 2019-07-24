package ru.kamishnikov.testapprss.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

import java.io.Serializable

@Root(name = "rss", strict = false)
class RssFeed : Serializable {

    @Attribute
    var version: String? = null

    @Element
    var channel: RssChannel? = null

    override fun toString(): String {
        return "RSS{" +
                "version='" + version + '\''.toString() +
                ", channel=" + channel +
                '}'.toString()
    }
}
