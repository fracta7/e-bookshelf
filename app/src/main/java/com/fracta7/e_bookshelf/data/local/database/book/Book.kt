package com.fracta7.e_bookshelf.data.local.database.book

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fracta7.e_bookshelf.domain.model.book.isbn.authors.AuthorsModel
import com.fracta7.e_bookshelf.domain.model.book.isbn.publishers.PublishPlacesModel
import com.fracta7.e_bookshelf.domain.model.book.isbn.publishers.PublishersModel

@Entity(tableName = "book")
data class Book(
    @PrimaryKey val uid: Int? = null,
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
