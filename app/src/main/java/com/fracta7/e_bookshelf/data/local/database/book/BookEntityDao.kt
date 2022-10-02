package com.fracta7.e_bookshelf.data.local.database.book

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookEntityDao {
    @Query("SELECT * FROM books")
    suspend fun getAll(): List<BookEntity>

    @Query("SELECT * FROM books WHERE uid IN (:bookIds)")
    suspend fun loadById(bookIds: IntArray): List<BookEntity>

    @Insert
    suspend fun insertBook(vararg books: BookEntity)

    @Query("SELECT * FROM books WHERE isbn = :isbn")
    suspend fun searchByISBN(isbn: String): BookEntity

    @Query("DELETE FROM books WHERE isbn = :isbn")
    suspend fun delete(isbn: String)

    @Query("DELETE FROM books")
    suspend fun deleteAll()
}