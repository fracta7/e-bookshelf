package com.fracta7.e_bookshelf.data.local.database.remote

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RawBookEntityDao {

    @Query("SELECT * FROM bookinfo")
    suspend fun getAll(): List<RawBookEntity>

    @Query("SELECT * FROM bookinfo WHERE uid IN (:bookIds)")
    suspend fun loadById(bookIds: IntArray): List<RawBookEntity>

    @Insert
    suspend fun insertBook(vararg books: RawBookEntity)

    @Query("SELECT * FROM bookinfo WHERE isbn = :isbn")
    suspend fun searchByISBN(isbn: String): RawBookEntity

    @Delete
    suspend fun delete(book: RawBookEntity)

    @Query("DELETE FROM bookinfo")
    suspend fun deleteAll()
}