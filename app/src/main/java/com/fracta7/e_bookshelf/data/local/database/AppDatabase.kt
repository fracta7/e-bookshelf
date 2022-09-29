package com.fracta7.e_bookshelf.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fracta7.e_bookshelf.data.local.database.book.BookEntity
import com.fracta7.e_bookshelf.data.local.database.book.BookEntityDao
import com.fracta7.e_bookshelf.data.local.database.book.ReadingListEntity
import com.fracta7.e_bookshelf.data.local.database.book.ReadingListEntityDao

@Database(
    entities = [BookEntity::class, ReadingListEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookEntityDao
    abstract fun readingList(): ReadingListEntityDao
}