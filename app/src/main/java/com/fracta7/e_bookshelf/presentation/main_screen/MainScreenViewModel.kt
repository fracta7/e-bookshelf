package com.fracta7.e_bookshelf.presentation.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fracta7.e_bookshelf.data.local.database.book.Book
import com.fracta7.e_bookshelf.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    fun triggerFlow(isbn: String): Book {
        val book = mutableStateOf(Book(0,"","","","","","",0,0,"","Default",false))
        viewModelScope.launch {
            repository.getBookByISBN("ISBN:$isbn").collectLatest {
                book.value = it
            }
        }
        return book.value
    }
}