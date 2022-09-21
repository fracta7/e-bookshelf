package com.fracta7.e_bookshelf.presentation.add_new_book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fracta7.e_bookshelf.domain.repository.AppRepository
import com.fracta7.e_bookshelf.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    var state by mutableStateOf(AddBookState())

    fun onEvent(event: AddBookEvent = AddBookEvent.AddBook) {
        when (event) {
            is AddBookEvent.AddBook -> {
                state = state.copy(url = "https://covers.openlibrary.org/b/id/7357496-L.jpg")
            }
            is AddBookEvent.SelectCategory -> {
                state = state.copy(selectedCategory = event.selectedItem)
            }
            is AddBookEvent.ToggleState -> {
                //we check if have some data to show before toggling info section
                state = state.copy(
                    manual = !state.manual,
                    isBookAvailable = if (state.status != "No result") {
                        !state.isBookAvailable
                    } else {
                        false
                    }
                )
            }
            is AddBookEvent.CheckBook -> {
                viewModelScope.launch {
                    repository.getBookByISBN(isbn = event.isbn).collect {
                        state = when (it) {
                            is Resource.Loading -> {
                                state.copy(status = "Loading...")
                            }
                            is Resource.Error -> {
                                state.copy(status = "No result", isBookAvailable = false)
                            }
                            is Resource.Success -> {
                                state.copy(
                                    book = it.data!!,
                                    status = "Found",
                                    author = it.data.author!!,
                                    publisher = it.data.publishers!!,
                                    publish_date = it.data.publish_date!!,
                                    page_count = it.data.page_count.toString(),
                                    weight = it.data.weight!!,
                                    publish_places = it.data.publish_places!!,
                                    isBookAvailable = true
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}