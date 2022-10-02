package com.fracta7.e_bookshelf.data.local.database.settings

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings ORDER BY ROWID ASC LIMIT 1")
    suspend fun getSettings(): SettingsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: SettingsEntity)

    @Query("DELETE FROM settings")
    suspend fun deleteSettings()

}