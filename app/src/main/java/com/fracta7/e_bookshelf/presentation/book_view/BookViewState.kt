package com.fracta7.e_bookshelf.presentation.book_view

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.fracta7.e_bookshelf.R
import com.fracta7.e_bookshelf.domain.model.Book

data class BookViewState(
    val book: Book? = null,
    val isEditMode: Boolean = false,
    val darkTheme: Boolean = true,
    val dynamicTheme: Boolean = false
)
