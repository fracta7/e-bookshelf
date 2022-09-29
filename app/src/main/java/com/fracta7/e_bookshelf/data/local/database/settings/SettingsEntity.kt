package com.fracta7.e_bookshelf.data.local.database.settings

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val uid: Int? = null,
    val darkTheme: Boolean?
)
