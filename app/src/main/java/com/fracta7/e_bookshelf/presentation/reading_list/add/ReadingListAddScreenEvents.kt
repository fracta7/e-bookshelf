package com.fracta7.e_bookshelf.presentation.reading_list.add

sealed class ReadingListAddScreenEvents {
    data class AddReadingList(val readingListName: String, val isDoneReading: Boolean = false): ReadingListAddScreenEvents()
}
