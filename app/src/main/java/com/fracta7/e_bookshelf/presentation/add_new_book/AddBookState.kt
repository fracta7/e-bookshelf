package com.fracta7.e_bookshelf.presentation.add_new_book

data class AddBookState constructor(
    val isBookAvailable: Boolean = true,
    val selectedCategory: String = ""
)
