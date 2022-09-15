package com.fracta7.e_bookshelf.presentation.add_new_book

sealed class AddBookEvent{
    object AddBook: AddBookEvent()
    data class SelectCategory(val selectedItem: String = ""): AddBookEvent()
    object ToggleState: AddBookEvent()
}