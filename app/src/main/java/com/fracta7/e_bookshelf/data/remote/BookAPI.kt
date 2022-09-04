package com.fracta7.e_bookshelf.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface BookAPI {
    //link for the API: https://openlibrary.org/api/books?bibkeys=ISBN:9780747532743&jscmd=data&format=json
    //link for the cover: https://covers.openlibrary.org/b/id/7357496-M.jpg
    //bibkeys=ISBN:9780747532743

    @GET("books?$OTHER_PARAMS")
    suspend fun getBookDetailsByISBN(
        @Query("bibkeys") isbn: String //ISBN:<isbn number>
    ) : ResponseBody

    companion object{
        const val BASE_URL = "https://openlibrary.org/api/"
        const val OTHER_PARAMS = "jscmd=data&format=json"
    }
}