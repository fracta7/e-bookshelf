package com.fracta7.e_bookshelf.presentation.main_screen

import com.fracta7.e_bookshelf.domain.model.Book

data class MainScreenState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val isbn: String = ""
)
