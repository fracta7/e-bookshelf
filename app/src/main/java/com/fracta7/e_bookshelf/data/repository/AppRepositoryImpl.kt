package com.fracta7.e_bookshelf.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.fracta7.e_bookshelf.data.local.database.AppDatabase
import com.fracta7.e_bookshelf.data.mapper.toBook
import com.fracta7.e_bookshelf.data.mapper.toBookEntity
import com.fracta7.e_bookshelf.data.remote.BookAPI
import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.ISBNModel
import com.fracta7.e_bookshelf.domain.repository.AppRepository
import com.fracta7.e_bookshelf.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

var book = mutableStateOf(
    Book(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        "",
        "",
        "",
        false
    )
)

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val bookAPI: BookAPI,
    database: AppDatabase
) : AppRepository {

    private val db = database.bookDao()

    /*
        override suspend fun networkCall(isbn: String): Resource<Book> {
            val call = bookAPI.getBookDetailsByISBN(isbn)
            call.enqueue(object : Callback<Map<String, ISBNModel>> {
                override fun onResponse(
                    call: Call<Map<String, ISBNModel>>,
                    response: Response<Map<String, ISBNModel>>
                ) {
                    val remoteInfoL = response.body()
                    book.value = remoteInfoL?.let {
                        convertResponseToBook(isbn, it)
                    }!!
                }

                override fun onFailure(call: Call<Map<String, ISBNModel>>, t: Throwable) {
                    Log.d("repository", "onFailure: $t")
                }
            })
            Log.d(TAG, "networkCall: $book")
            return Resource.Success(book.value)
        }
    */
    private fun convertResponseToBook(
        isbn: String,
        remoteInfo: Map<String, ISBNModel>,
        category: String
    ): Book {
        var authors: String? = ""
        var publishers: String? = ""
        var publishPlaces = ""
        val image = null

        remoteInfo[isbn]?.authors?.forEach {
            authors += it.name + ", "
        }
        val url: String = remoteInfo[isbn]?.url.toString()
        val title: String = remoteInfo[isbn]?.title.toString()
        remoteInfo[isbn]?.publishers?.forEach {
            publishers += it.name + ", "
        }
        remoteInfo[isbn]?.publish_places?.forEach {
            publishPlaces += it.name + ", "
        }
        val publishDate: String = remoteInfo[isbn]?.publish_date.toString()
        val numberOfPages = remoteInfo[isbn]?.number_of_pages
        val weight: String = remoteInfo[isbn]?.weight.toString()

        Log.d(TAG, "getBookByISBN: $authors")

        return Book(
            url = url,
            title = title,
            publishers = publishers,
            publish_places = publishPlaces,
            publish_date = publishDate,
            cover = image,
            number_of_pages = numberOfPages,
            weight = weight,
            authors = authors,
            category = category,
            favorite = false,
            isbn = isbn,
            description = null
        )
    }

    override suspend fun getBookByISBN(
        isbn: String,
        remoteFetch: Boolean,
        category: String
    ): Flow<Resource<Book>> {

        return flow {
            emit(Resource.Loading(true))
            val localBook = db.searchByISBN(isbn)
            val localBooks = db.getAll()
            emit(Resource.Success(localBook.toBook()))

            val isDbEmpty = localBooks.isEmpty()
            val shouldLoadFromCache = !isDbEmpty && !remoteFetch

            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            var remoteBook: Resource<Book> = Resource.Loading(true)
            try {
                val response = bookAPI.getBookByISBN(isbn)
                response.enqueue(object : Callback<Map<String, ISBNModel>> {
                    override fun onResponse(
                        call: Call<Map<String, ISBNModel>>,
                        response: Response<Map<String, ISBNModel>>
                    ) {
                        val book =
                            response.body()?.let { convertResponseToBook(isbn, it, category) }
                        remoteBook = Resource.Success(book)
                    }

                    override fun onFailure(call: Call<Map<String, ISBNModel>>, t: Throwable) {
                        t.printStackTrace()
                        remoteBook = Resource.Error("Couldn't get data. $t")
                    }

                })
            } catch (e: IOException) {
                emit(Resource.Error("IOException"))
            } catch (e: HttpException) {
                emit(Resource.Error("HTTPException"))
            }

            //insert new data to the database then emit from db
            remoteBook.data?.toBookEntity()?.let { db.insertBook(it) }
            emit(Resource.Success(db.searchByISBN(isbn).toBook()))
        }
    }

    override suspend fun getAllBooks(): Flow<List<Book>> {
        return flow {
            emit(db.getAll().map { it.toBook() })
        }
    }

}
