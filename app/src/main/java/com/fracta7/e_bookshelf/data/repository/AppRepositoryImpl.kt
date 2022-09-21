package com.fracta7.e_bookshelf.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.fracta7.e_bookshelf.data.local.database.AppDatabase
import com.fracta7.e_bookshelf.data.mapper.toBook
import com.fracta7.e_bookshelf.data.mapper.toRawBook
import com.fracta7.e_bookshelf.data.mapper.toRawBookEntity
import com.fracta7.e_bookshelf.data.remote.BookAPI
import com.fracta7.e_bookshelf.data.remote.dto.book.isbn.ISBNModel
import com.fracta7.e_bookshelf.domain.model.Book
import com.fracta7.e_bookshelf.domain.model.RawBook
import com.fracta7.e_bookshelf.domain.repository.AppRepository
import com.fracta7.e_bookshelf.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val bookAPI: BookAPI,
    private val database: AppDatabase
) : AppRepository {

    private val db = database

    private fun convertResponseToBook(
        isbn: String,
        remoteInfo: Map<String, ISBNModel>
    ): RawBook {
        var authors: String = ""
        var publishers: String = ""
        var publishPlaces = ""
        var url: String = ""
        var title: String = ""
        var publishDate: String = ""
        var numberOfPages: Int = 0
        var weight: String = ""
        var image: String = ""

        if (!remoteInfo[isbn]?.authors.isNullOrEmpty()) {
            remoteInfo[isbn]?.authors?.forEach {
                authors += it.name + ", "
            }
        }

        if (remoteInfo[isbn]?.url != null) {
            url = remoteInfo[isbn]?.url.toString()

        }
        if (remoteInfo[isbn]?.title != null) {
            title = remoteInfo[isbn]?.title.toString()
        }
        if (!remoteInfo[isbn]?.publishers.isNullOrEmpty()) {
            remoteInfo[isbn]?.publishers?.forEach {
                publishers += it.name + ", "
            }
        }
        if (!remoteInfo[isbn]?.publish_places.isNullOrEmpty()) {
            remoteInfo[isbn]?.publish_places?.forEach {
                publishPlaces += it.name + ", "
            }
        }
        if (remoteInfo[isbn]?.publish_date != null) {
            publishDate = remoteInfo[isbn]?.publish_date.toString()
        }
        if (remoteInfo[isbn]?.number_of_pages != null) {
            numberOfPages = remoteInfo[isbn]?.number_of_pages!!
        }
        if (remoteInfo[isbn]?.weight != null) {
            weight = remoteInfo[isbn]?.weight.toString()
        }
        if (remoteInfo[isbn]?.cover?.large != null) {
            image = remoteInfo[isbn]?.cover?.large!!
        }

        return RawBook(
            url = url,
            title = title,
            publishers = publishers,
            publish_places = publishPlaces,
            publish_date = publishDate,
            cover = image,
            page_count = numberOfPages,
            weight = weight,
            author = authors,
            isbn = isbn
        )
    }

    override suspend fun getBookByISBN(
        isbn: String
    ): Flow<Resource<RawBook>> {
        val bookdb = db.bookInfoDao()
        return flow {
            emit(Resource.Loading(true))

            val localBooks = bookdb.getAll()
            val isDbEmpty = localBooks.isEmpty()

            if (!isDbEmpty) {
                val localBook = bookdb.searchByISBN(isbn)
                emit(Resource.Success(localBook.toRawBook()))
            }

            val shouldLoadFromCache = !isDbEmpty

            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            var remoteBook: Resource<RawBook> = Resource.Loading(true)
            try {
                val response = bookAPI.getBookByISBN(isbn)
                Log.d(TAG, "getBookByISBN: $response")
                if (response.isEmpty()) {
                    emit(Resource.Error("not found"))
                } else {
                    val data = convertResponseToBook(isbn, response)
                    remoteBook = Resource.Success(data)
                    emit(Resource.Success(data))
                }

            } catch (e: IOException) {
                emit(Resource.Error("IOException"))

            } catch (e: HttpException) {
                emit(Resource.Error("HTTPException"))
            }


            //insert new data to the database then emit from db
            val data = remoteBook.data?.toRawBookEntity()
            if (data != null) {
                emit(Resource.Success(data.toRawBook()))
            }
        }
    }

    override suspend fun getAllBooks(): Flow<List<Book>> {
        return flow {
            emit(db.bookDao().getAll().map { it.toBook() })
        }
    }

}
