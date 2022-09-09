package com.fracta7.e_bookshelf.domain.model

data class Book(
    val isbn: String?,
    val url: String?,
    val title: String?,
    val authors: String?,
    val publishers: String?,
    val publish_places: String?,
    val publish_date: String?,
    val cover: Int?,
    val number_of_pages: Int?,
    val weight: String?,
    val category: String? = "Default",
    val favorite: Boolean?
)
