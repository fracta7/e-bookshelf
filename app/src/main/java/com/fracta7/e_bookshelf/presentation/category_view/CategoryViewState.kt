package com.fracta7.e_bookshelf.presentation.category_view

import com.fracta7.e_bookshelf.domain.model.Book

data class CategoryViewState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val isDbEmpty: Boolean = true,
    val isEditMode: Boolean = false
)
