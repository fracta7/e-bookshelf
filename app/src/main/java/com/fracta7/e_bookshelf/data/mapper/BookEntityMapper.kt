package com.fracta7.e_bookshelf.data.mapper

import com.fracta7.e_bookshelf.data.local.database.book.BookEntity
import com.fracta7.e_bookshelf.domain.model.Book

fun BookEntity.toBook(): Book {
    return Book(
        title = title,
        authors = authors,
        publishers = publishers,
        publish_places = publish_places,
        publish_date = publish_date,
        cover = cover,
        number_of_pages = number_of_pages,
        weight = weight,
        readingList = readingList,
        favorite = favorite,
        isbn = isbn,
        description = description,
        genre = genre
    )
}

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        title = title,
        authors = authors,
        publishers = publishers,
        publish_places = publish_places,
        publish_date = publish_date,
        cover = cover,
        number_of_pages = number_of_pages,
        weight = weight,
        readingList = readingList,
        favorite = favorite,
        isbn = isbn,
        description = description,
        genre = genre
    )
}