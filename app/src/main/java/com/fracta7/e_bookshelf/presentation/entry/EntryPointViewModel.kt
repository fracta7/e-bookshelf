package com.fracta7.e_bookshelf.presentation.entry

import androidx.compose.animation.core.TweenSpec
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fracta7.e_bookshelf.domain.model.Genres
import com.fracta7.e_bookshelf.domain.repository.AppRepository
import com.fracta7.e_bookshelf.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryPointViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    var state by mutableStateOf(EntryPointState())

    init {
        onEvent(EntryPointEvent.LoadDatabase)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onEvent(event: EntryPointEvent) {
        when (event) {
            is EntryPointEvent.ChangeTheme -> {
                state = state.copy(darkTheme = !state.darkTheme)
            }
            is EntryPointEvent.LoadDatabase -> {
                viewModelScope.launch {
                    repository.getAllBooks().collect {
                        state = when (it) {
                            is Resource.Success -> {
                                state.copy(
                                    books = it.data!!,
                                    isLoading = false,
                                    isDbEmpty = false
                                )
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
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun openDrawer() {
        state.drawerState.animateTo(DrawerValue.Open, TweenSpec(200))
    }
}