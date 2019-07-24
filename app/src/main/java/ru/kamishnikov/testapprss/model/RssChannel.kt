package ru.kamishnikov.testapprss.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

import java.io.Serializable

@Root(strict = false)
class RssChannel(
    @ElementList(name = "item", required = true, inline = true)
    var itemList: List<RssFeedItem>
) : Serializable
