package com.fracta7.e_bookshelf.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.fracta7.e_bookshelf.data.local.database.book.Book
import com.fracta7.e_bookshelf.data.remote.BookAPI
import com.fracta7.e_bookshelf.domain.model.book.isbn.ISBNModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

var book = mutableStateOf(Book(null, "", "", "", "", "", "", 0, 0, ""))

class AppRepository @Inject constructor(
    private val bookAPI: BookAPI
) {
    private var remoteInfo: Map<String, ISBNModel>? = null

    fun networkCall(isbn: String): Book {
        val call = bookAPI.getBookDetailsByISBN(isbn)
        call.enqueue(object : Callback<Map<String, ISBNModel>> {
            override fun onResponse(
                call: Call<Map<String, ISBNModel>>,
                response: Response<Map<String, ISBNModel>>
            ) {
                val remoteInfoL = response.body()
                book.value = remoteInfoL?.let { convertResponseToBook(isbn, it) }!!
            }

            override fun onFailure(call: Call<Map<String, ISBNModel>>, t: Throwable) {
                Log.d("repository", "onFailure: $t")
            }
        })
        Log.d(TAG, "networkCall: $book")
        return book.value
    }

    fun convertResponseToBook(isbn: String, remoteInfo: Map<String, ISBNModel>): Book {

        var authors: String? = ""
        var url = ""
        var title = ""
        var publishers: String? = ""
        var publish_places: String = ""
        var publish_date = ""
        val image = null
        var number_of_pages: Int? = 0
        var weight = ""

        remoteInfo[isbn]?.authors?.forEach {
            authors += it.name + ", "
        }
        url = remoteInfo[isbn]?.url.toString()
        title = remoteInfo[isbn]?.title.toString()
        remoteInfo[isbn]?.publishers?.forEach {
            publishers += it.name + ", "
        }
        remoteInfo[isbn]?.publish_places?.forEach {
            publish_places += it.name + ", "
        }
        publish_date = remoteInfo[isbn]?.publish_date.toString()
        number_of_pages = remoteInfo[isbn]?.number_of_pages
        weight = remoteInfo[isbn]?.weight.toString()

        Log.d(TAG, "getBookByISBN: $authors")

        return Book(
            url = url,
            title = title,
            publishers = publishers,
            publish_places = publish_places,
            publish_date = publish_date,
            cover = image,
            number_of_pages = number_of_pages,
            weight = weight,
            authors = authors
        )
    }

    fun getBookByISBN(isbn: String): Flow<Book> {
        networkCall(isbn)
        val bookFlow = flow<Book>{
            emit(book.value)
        }
        return bookFlow
    }

}
