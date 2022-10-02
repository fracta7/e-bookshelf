package com.fracta7.e_bookshelf.presentation.add_new_book

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.domain.repository.AppRepository
import com.fracta7.e_bookshelf.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(private val repository: AppRepository, val imageLoader: ImageLoader) : ViewModel() {
    var state by mutableStateOf(AddBookState())
    var im = imageLoader

    init {
        viewModelScope.launch {
            repository.getAllBooks().collect {
                when (it) {
                    is Resource.Loading -> Unit
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        state = state.copy(books = it.data!!)
                    }
                }
            }
        }
        viewModelScope.launch {
            repository.getDarkSettings().collect{
                state = state.copy(darkTheme = it)
            }
        }
        viewModelScope.launch {
            repository.getDynamicSettings().collect{
                state = state.copy(dynamicTheme = it)
            }
        }
    }

    fun onEvent(event: AddBookEvent = AddBookEvent.AddBook) {
        when (event) {
            is AddBookEvent.AddBook -> {
                viewModelScope.launch {
                    val book = Book(
                        isbn = state.isbn,
                        cover = state.url,
                        title = state.title,
                        authors = state.author,
                        publishers = state.publisher,
                        publish_date = state.publish_date,
                        publish_places = state.publish_places,
                        number_of_pages = if (state.page_count.isNotBlank()) state.page_count.toInt() else 0,
                        weight = state.weight,
                        genre = state.selectedCategory,
                        readingList = state.selectedReadingList,
                        description = state.description,
                        favorite = false
                    )
                    Log.d(TAG, "onEvent: $book")
                    repository.insertBook(book)
                }

                //placeholder
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
            //executed when typing to isbn textfield
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
                                    isBookAvailable = true,
                                    title = it.data.title!!,
                                    isbn = "ISBN:${event.isbn}",
                                    url = it.data.cover!!
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}