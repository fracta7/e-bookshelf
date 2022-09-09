package com.fracta7.e_bookshelf.data.local.database.book

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookEntityDao {
    @Query("SELECT * FROM book")
    suspend fun getAll(): List<BookEntity>

    @Query("SELECT * FROM book WHERE uid IN (:bookIds)")
    suspend fun loadById(bookIds: IntArray): List<BookEntity>

    @Insert
    suspend fun insertBook(vararg books: BookEntity)

    @Query("SELECT * FROM book WHERE isbn = :isbn")
    suspend fun searchByISBN(isbn: String): BookEntity

    @Delete
    suspend fun delete(bookEntity: BookEntity)

    @Query("DELETE FROM book")
    suspend fun deleteAll()
}