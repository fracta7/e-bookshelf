package com.fracta7.e_bookshelf.data.mapper

import com.fracta7.e_bookshelf.data.local.database.remote.RawBookEntity
import com.fracta7.e_bookshelf.domain.model.RawBook

fun RawBookEntity.toRawBook(): RawBook {
    return RawBook(
        author = author,
        publishers = publishers,
        publish_date = publish_date,
        url = url,
        publish_places = publish_places,
        cover = cover,
        page_count = page_count,
        weight = weight,
        isbn = isbn,
        title = title
    )
}

fun RawBook.toRawBookEntity(): RawBookEntity {
    return RawBookEntity(
        author = author,
        publishers = publishers,
        publish_date = publish_date,
        url = url,
        publish_places = publish_places,
        cover = cover,
        page_count = page_count,
        weight = weight,
        isbn = isbn,
        title = title
    )
}