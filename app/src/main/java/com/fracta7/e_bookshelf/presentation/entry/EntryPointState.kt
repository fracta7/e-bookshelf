package com.fracta7.e_bookshelf.presentation.entry

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.domain.model.FilteredGenre
import com.fracta7.e_bookshelf.domain.model.ReadingList

data class EntryPointState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val darkTheme: Boolean = true,
    val isLoading: Boolean = false,
    val drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    val isDbEmpty: Boolean = true,
    val dynamicTheme: Boolean = false,
    val books: List<Book> = emptyList(),
    val readingList: List<ReadingList> = emptyList()
)