package com.fracta7.e_bookshelf.data.remote

import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.ISBNModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookAPI {
    //link for the API: https://openlibrary.org/api/books?bibkeys=ISBN:9780747532743&jscmd=data&format=json
    //link for the cover: https://covers.openlibrary.org/b/id/7357496-M.jpg
    //bibkeys=ISBN:9780747532743

    @GET("books?jscmd=data&format=json")
    fun getBookDetailsByISBN(@Query("bibkeys") isbn: String): Call<Map<String, ISBNModel>>

    @GET("books?jscmd=data&format=json")
    suspend fun getBookByISBN(@Query("bibkeys") isbn: String): Call<Map<String, ISBNModel>>

    companion object {
        const val BASE_URL = "https://openlibrary.org/api/"
    }
}