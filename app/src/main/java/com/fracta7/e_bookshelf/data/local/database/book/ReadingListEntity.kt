package com.fracta7.e_bookshelf.data.local.database.book

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_list")
data class ReadingListEntity(
    @PrimaryKey val uid: Int? = null,
    val name: String?,
    val isDone: Boolean?
)
