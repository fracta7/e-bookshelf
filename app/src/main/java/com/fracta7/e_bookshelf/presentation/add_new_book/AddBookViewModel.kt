package com.fracta7.e_bookshelf.presentation.add_new_book

import androidx.compose.animation.core.TweenSpec
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(AddBookState())

    fun onEvent(event: AddBookEvent = AddBookEvent.AddBook) {
        when (event) {
            is AddBookEvent.AddBook -> {

            }
            is AddBookEvent.SelectCategory -> {
                state = state.copy(selectedCategory = event.selectedItem)
            }
            is AddBookEvent.ToggleState -> {
                state = state.copy(isBookAvailable = !state.isBookAvailable)
            }
        }
    }
}