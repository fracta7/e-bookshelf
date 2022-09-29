package com.fracta7.e_bookshelf.presentation.book_view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fracta7.e_bookshelf.data.mapper.toBookEntity
import com.fracta7.e_bookshelf.domain.repository.AppRepository
import com.fracta7.e_bookshelf.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    var state by mutableStateOf(BookViewState())

    fun onEvent(event: BookViewEvent){
        when(event){
            is BookViewEvent.LoadBook->{
                viewModelScope.launch {
                    repository.getAllBooks().collect{ it1->
                        when(it1){
                            is Resource.Error -> Unit
                            is Resource.Loading -> Unit
                            is Resource.Success -> {
                                it1.data?.forEach {
                                    if (it.isbn==event.isbn){
                                        state = state.copy(book = it)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            is BookViewEvent.DeleteBook->{
                viewModelScope.launch {
                    repository.deleteBook(event.isbn)
                }
            }
        }
    }
}