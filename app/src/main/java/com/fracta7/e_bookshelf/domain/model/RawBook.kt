package com.fracta7.e_bookshelf.domain.model

data class RawBook(
    val author: String?,
    val publishers: String?,
    val publish_date: String?,
    val url: String?,
    val publish_places: String?,
    val cover: String?,
    val page_count: Int?,
    val weight: String?,
    val isbn: String?,
    val title: String?
)
