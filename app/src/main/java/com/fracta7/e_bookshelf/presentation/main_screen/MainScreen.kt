package com.fracta7.e_bookshelf.presentation.main_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.presentation.composable_elements.LibraryCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(contentPadding: PaddingValues,categories: List<String>, books: List<Book>) {
    val viewModel = hiltViewModel<MainScreenViewModel>()
    LazyColumn(
        contentPadding = contentPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        items(categories.size) {
            LibraryCategory(
                title = categories[it],
                books = books,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
