package com.fracta7.e_bookshelf.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fracta7.e_bookshelf.data.local.database.book.BookEntity
import com.fracta7.e_bookshelf.data.local.database.book.BookEntityDao

@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookDao(): BookEntityDao
}