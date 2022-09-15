package com.fracta7.e_bookshelf.domain.repository

import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {
//    suspend fun networkCall(isbn: String): Resource<Book>
    suspend fun getBookByISBN(isbn: String, remoteFetch: Boolean = false,  category: String): Flow<Resource<Book>>
    suspend fun getAllBooks(): Flow<List<Book>>
}