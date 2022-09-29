package com.fracta7.e_bookshelf.presentation.book_view

import com.fracta7.e_bookshelf.domain.model.Book

sealed class BookViewEvent{
    data class LoadBook(val isbn: String): BookViewEvent()
    data class DeleteBook(val isbn: String) : BookViewEvent()
}
