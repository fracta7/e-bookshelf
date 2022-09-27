package com.fracta7.e_bookshelf.presentation.add_new_book

import com.fracta7.e_bookshelf.domain.model.RawBook

data class AddBookState(
    val isBookAvailable: Boolean = false,
    val selectedCategory: String = "",
    val url: String = "",
    val status: String = "",
    val book: RawBook = RawBook(
        author = "",
        publishers = "",
        publish_date = "",
        url = "",
        publish_places = "",
        cover = "",
        page_count = 0,
        weight = "",
        isbn = "",
        title = ""
    ),
    val title: String = "",
    val author: String = "",
    val publisher: String = "",
    val publish_date: String = "",
    val page_count: String = "0",
    val description: String = "",
    val publish_places: String = "",
    val weight: String = "",
    val manual: Boolean = false
)
