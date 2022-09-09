package com.fracta7.e_bookshelf.presentation.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fracta7.e_bookshelf.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    var state by mutableStateOf(MainScreenState())

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.AddNewBook -> {

            }
            is MainScreenEvent.OnSearchQueryChange -> {

            }
        }
    }

    fun getBooks() {
        viewModelScope.launch {
            repository
                .getAllBooks()
                .collect {
                    state = state.copy(books = it)
                }
        }
    }
}