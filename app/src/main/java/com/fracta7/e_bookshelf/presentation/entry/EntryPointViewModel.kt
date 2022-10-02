@file:OptIn(ExperimentalMaterial3Api::class)

package com.fracta7.e_bookshelf.presentation.entry

import androidx.compose.animation.core.TweenSpec
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fracta7.e_bookshelf.domain.model.Genres
import com.fracta7.e_bookshelf.domain.model.Settings
import com.fracta7.e_bookshelf.domain.repository.AppRepository
import com.fracta7.e_bookshelf.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryPointViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    var state by mutableStateOf(EntryPointState())

    init {
        onEvent(EntryPointEvent.LoadDatabase)
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

    @OptIn(ExperimentalMaterial3Api::class)
    fun onEvent(event: EntryPointEvent) {
        when (event) {
            is EntryPointEvent.ChangeTheme -> {
                state = state.copy(darkTheme = !state.darkTheme)
                viewModelScope.launch {
                    repository.setDarkSettings(state.darkTheme)
                }
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
            is EntryPointEvent.ToggleDynamicTheme->{
                state = state.copy(dynamicTheme = !state.dynamicTheme)
                viewModelScope.launch {
                    repository.setDynamicSettings(state.dynamicTheme)
                }
            }
            is EntryPointEvent.DeleteDb->{
                state = state.copy(books = emptyList())
                viewModelScope.launch {
                    repository.deleteAll()
                    state = state.copy(isDbEmpty = true)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun openDrawer() {
        state.drawerState.animateTo(DrawerValue.Open, TweenSpec(200))
    }

    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun closeDrawer(){
        state.drawerState.animateTo(DrawerValue.Closed, TweenSpec(200))
    }
}