package com.fracta7.e_bookshelf.data.mapper

import com.fracta7.e_bookshelf.data.local.database.settings.SettingsEntity
import com.fracta7.e_bookshelf.domain.model.Settings

fun SettingsEntity.toSettings(): Settings{
    return Settings(darkTheme = darkTheme)
}

fun Settings.toSettingsEntity(): SettingsEntity{
    return SettingsEntity(darkTheme = darkTheme)
}