package com.fracta7.e_bookshelf.presentation.reading_list

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
class ReadingListAllViewModel @Inject constructor(
    repository: AppRepository
) : ViewModel() {
    var state by mutableStateOf(ReadingListAllState())

    init {
        viewModelScope.launch {
            repository.getAllBooks().collect {
                state = when (it) {
                    is Resource.Success -> {
                        state.copy(books = it.data!!, isDbEmpty = false, isLoading = false)
                    }
                    is Resource.Error -> {
                        state.copy(isDbEmpty = true, isLoading = false)
                    }
                    is Resource.Loading -> {
                        state.copy(isLoading = true)
                    }
                }
            }
        }
        viewModelScope.launch {
            repository.getDarkSettings().collect {
                state = state.copy(darkTheme = it)
            }
        }
        viewModelScope.launch {
            repository.getDynamicSettings().collect {
                state = state.copy(dynamicTheme = it)
            }
        }
    }
}