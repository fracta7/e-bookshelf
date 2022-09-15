package com.fracta7.e_bookshelf.presentation.entry

sealed class EntryPointEvent{
    object ChangeTheme: EntryPointEvent()
    object OpenDrawer: EntryPointEvent()
    object AddBook: EntryPointEvent()
}
