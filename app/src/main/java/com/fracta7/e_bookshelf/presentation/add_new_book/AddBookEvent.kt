package com.fracta7.e_bookshelf.presentation.add_new_book

sealed class AddBookEvent {
    object AddBook : AddBookEvent()

    data class SelectCategory(val selectedItem: String = "") : AddBookEvent()
    object ToggleState : AddBookEvent()
    data class CheckBook(val isbn: String) : AddBookEvent()
    data class SelectReadingList(val selectedReadingList: String = "Default") : AddBookEvent()
}
