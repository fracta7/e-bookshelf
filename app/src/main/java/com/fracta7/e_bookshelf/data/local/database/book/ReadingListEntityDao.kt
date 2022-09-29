package com.fracta7.e_bookshelf.data.local.database.book

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReadingListEntityDao {
    @Query("SELECT * FROM reading_list")
    suspend fun getAll(): List<ReadingListEntity>

    @Insert
    suspend fun insertBook(vararg readingList: ReadingListEntity)

    @Delete
    suspend fun delete(readingList: ReadingListEntity)

    @Query("DELETE FROM reading_list")
    suspend fun deleteAll()
}