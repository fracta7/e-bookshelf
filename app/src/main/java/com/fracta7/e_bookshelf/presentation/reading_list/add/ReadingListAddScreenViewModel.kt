package com.fracta7.e_bookshelf.presentation.reading_list.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fracta7.e_bookshelf.domain.model.ReadingList
import com.fracta7.e_bookshelf.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingListAddScreenViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    var state by mutableStateOf(ReadingListAddScreenState())

    init {
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

    fun onEvent(event: ReadingListAddScreenEvents) {
        when (event) {
            is ReadingListAddScreenEvents.AddReadingList -> {
                //we check if it already exists before adding conflicting one
                val alreadyExists = state.readingList.contains(
                    ReadingList(
                        name = event.readingListName,
                        isDone = false
                    )
                ) || state.readingList.contains(
                    ReadingList(
                        name = event.readingListName,
                        isDone = true
                    )
                )
                state = state.copy(alreadyExists = alreadyExists)
                if (!alreadyExists) {
                    viewModelScope.launch {
                        repository.addReadingList(
                            ReadingList(
                                name = event.readingListName,
                                isDone = event.isDoneReading
                            )
                        )
                    }
                }
            }
        }
    }
}