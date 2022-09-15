package com.fracta7.e_bookshelf.presentation.entry

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api

data class EntryPointState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val darkTheme: Boolean = true,
    val isLoading: Boolean = false,
    val drawerState: DrawerState = DrawerState(DrawerValue.Closed)
)