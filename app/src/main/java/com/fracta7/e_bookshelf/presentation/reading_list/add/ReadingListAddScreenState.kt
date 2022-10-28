package com.fracta7.e_bookshelf.presentation.reading_list.add

import com.fracta7.e_bookshelf.domain.model.ReadingList

data class ReadingListAddScreenState(
    val darkTheme: Boolean = false,
    val dynamicTheme: Boolean = false,
    val readingListName: String = "",
    val alreadyExists: Boolean = false,
    val readingList: List<ReadingList> = emptyList(),
)
