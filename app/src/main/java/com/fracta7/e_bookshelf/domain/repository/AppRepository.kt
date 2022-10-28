package com.fracta7.e_bookshelf.domain.repository

import androidx.datastore.preferences.core.Preferences
import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.domain.model.RawBook
import com.fracta7.e_bookshelf.domain.model.ReadingList
import com.fracta7.e_bookshelf.domain.model.Settings
import com.fracta7.e_bookshelf.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    //    suspend fun networkCall(isbn: String): Resource<Book>
    suspend fun getBookByISBN(
        isbn: String
    ): Flow<Resource<RawBook>>

    suspend fun getAllBooks(): Flow<Resource<List<Book>>>

    suspend fun insertBook(book: Book)

    suspend fun deleteBook(isbn: String)

    suspend fun deleteAll()

    suspend fun getDarkSettings(): Flow<Boolean>

    suspend fun getDynamicSettings(): Flow<Boolean>

    suspend fun setDarkSettings(dark: Boolean)

    suspend fun setDynamicSettings(dynamic: Boolean)

    suspend fun getReadingList(): Flow<Resource<List<ReadingList>>>

    suspend fun addReadingList(readingList: ReadingList)
}