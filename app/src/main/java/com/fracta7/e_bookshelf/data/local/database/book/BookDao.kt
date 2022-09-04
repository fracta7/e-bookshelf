package com.fracta7.e_bookshelf.data.local.database.book

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    suspend fun getAll(): List<Book>

    @Query("SELECT * FROM book WHERE uid IN (:bookIds)")
    suspend fun loadById(bookIds: IntArray): List<Book>

    @Insert
    suspend fun insertBook(vararg books: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("DELETE FROM book")
    suspend fun deleteAll()
}