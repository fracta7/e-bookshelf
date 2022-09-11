package com.fracta7.e_bookshelf.presentation.main_activity

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(MainActivityState())

    @OptIn(ExperimentalMaterial3Api::class)
    fun onEvent(event: MainActivityEvent) {
        when (event) {
            is MainActivityEvent.ChangeTheme -> {
                state = state.copy(darkTheme = !state.darkTheme)
            }
            is MainActivityEvent.OpenDrawer -> Unit

        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun openDrawer(){
        state.drawerState.animateTo(DrawerValue.Open, TweenSpec(200))
    }
}


