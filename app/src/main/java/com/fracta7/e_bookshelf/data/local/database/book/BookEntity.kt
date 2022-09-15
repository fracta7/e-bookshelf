package com.fracta7.e_bookshelf.data.local.database.book

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey val uid: Int? = null,
    val isbn: String?,
    val url: String?,
    val title: String?,
    val authors: String?,
    val publishers: String?,
    val publish_places: String?,
    val publish_date: String?,
    val cover: String?,
    val number_of_pages: Int?,
    val weight: String?,
    val readingList: String? = "Default",
    val description: String?,
    val favorite: Boolean?
)
