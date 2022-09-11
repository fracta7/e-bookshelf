package com.fracta7.e_bookshelf.presentation.main_activity

sealed class MainActivityEvent{
    object ChangeTheme: MainActivityEvent()
    object OpenDrawer: MainActivityEvent()
}
