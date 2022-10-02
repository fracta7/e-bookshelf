package com.fracta7.e_bookshelf.data.repository

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.fracta7.e_bookshelf.data.local.database.AppDatabase
import com.fracta7.e_bookshelf.data.mapper.toBook
import com.fracta7.e_bookshelf.data.mapper.toBookEntity
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
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val bookAPI: BookAPI,
    private val db: AppDatabase,
    private val application: Application
) : AppRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    val darkTheme = booleanPreferencesKey("dark_theme")
    val dynamicTheme = booleanPreferencesKey("dynamic_theme")

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

        return flow {
            emit(Resource.Loading(true))
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

    override suspend fun getAllBooks(): Flow<Resource<List<Book>>> {
        return flow {
            emit(Resource.Loading())
            val books = db.bookDao().getAll()
            if (books.isEmpty()) {
                emit(Resource.Error("Database is empty"))
            } else {
                emit(Resource.Success(data = books.map { it.toBook() }))
            }
        }
    }

    override suspend fun insertBook(book: Book) {
        db.bookDao().insertBook(book.toBookEntity())
    }

    override suspend fun deleteBook(isbn: String) {
        db.bookDao().delete(isbn)
    }

    override suspend fun getDynamicSettings(): Flow<Boolean> {
        return application.dataStore.data.map {
            it[dynamicTheme] ?: false
        }
    }

    override suspend fun setDynamicSettings(dynamic: Boolean) {
        application.dataStore.edit {
            it[dynamicTheme] = dynamic
        }
    }

    override suspend fun getDarkSettings(): Flow<Boolean> {
        return application.dataStore.data.map {
            it[darkTheme] ?: true
        }
    }

    override suspend fun setDarkSettings(dark: Boolean) {
        application.dataStore.edit {
            it[darkTheme] = dark
        }
    }

    override suspend fun deleteAll() {
        db.bookDao().deleteAll()
    }

}
