package com.fracta7.e_bookshelf.presentation.main_screen

import androidx.compose.material3.ColorScheme
import com.fracta7.e_bookshelf.domain.model.Book

sealed class MainScreenEvent {
    data class OnSearchQueryChange(val query: String) : MainScreenEvent()
    data class AddNewBook(val book: Book) : MainScreenEvent()
    data class SelectBook(val book: Book) : MainScreenEvent()
    class ChangeTheme() : MainScreenEvent()
}
