package com.fracta7.e_bookshelf.presentation.entry

sealed class EntryPointEvent{
    object ChangeTheme: EntryPointEvent()
    object ToggleDynamicTheme: EntryPointEvent()
    object DeleteDb: EntryPointEvent()
    object LoadDatabase: EntryPointEvent()
}
