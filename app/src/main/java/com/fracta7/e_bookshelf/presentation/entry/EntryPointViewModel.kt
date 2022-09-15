package com.fracta7.e_bookshelf.presentation.entry

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
class EntryPointViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(EntryPointState())

    @OptIn(ExperimentalMaterial3Api::class)
    fun onEvent(event: EntryPointEvent) {
        when (event) {
            is EntryPointEvent.ChangeTheme -> {
                state = state.copy(darkTheme = !state.darkTheme)
            }
            is EntryPointEvent.OpenDrawer -> Unit
            is EntryPointEvent.AddBook ->{

            }

        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun openDrawer(){
        state.drawerState.animateTo(DrawerValue.Open, TweenSpec(200))
    }
}