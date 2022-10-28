package com.fracta7.e_bookshelf.presentation.reading_list

import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.domain.model.ReadingList

data class ReadingListAllState(
    val darkTheme: Boolean = false,
    val dynamicTheme: Boolean = false,
    val books: List<Book> = emptyList(),
    val readingList: List<ReadingList> = emptyList(),
    val isLoading: Boolean = false,
    val isDbEmpty: Boolean = true
)
