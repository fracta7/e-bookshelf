package com.fracta7.e_bookshelf.presentation.category_view

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
class CategoryViewViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    var state by mutableStateOf(CategoryViewState())

    init {
        onEvent(CategoryViewEvent.LoadBooks)
    }

    fun onEvent(event: CategoryViewEvent) {
        when (event) {
            is CategoryViewEvent.LoadBooks -> {
                viewModelScope.launch {
                    repository.getAllBooks().collect {
                        when (it) {
                            is Resource.Loading -> {
                                state = state.copy(isLoading = true)
                            }
                            is Resource.Success -> {
                                state = state.copy(books = it.data!!, isDbEmpty = false, isLoading = false)
                            }
                            is Resource.Error -> {
                                state = state.copy(isDbEmpty = true, isLoading = false)
                            }
                        }
                    }
                }
            }
            is CategoryViewEvent.ToggleEditMode ->{
                state = state.copy(isEditMode = !state.isEditMode)
            }
        }
    }
}