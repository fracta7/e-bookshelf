package com.fracta7.e_bookshelf.di

import android.app.Application
import androidx.room.Room
import com.fracta7.e_bookshelf.data.local.database.AppDatabase
import com.fracta7.e_bookshelf.data.remote.BookAPI
import com.fracta7.e_bookshelf.data.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookAPI(): BookAPI {
        return Retrofit.Builder()
            .baseUrl(BookAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDB(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "book-database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideAppRepository(bookAPI: BookAPI): AppRepositoryImpl {
        return AppRepositoryImpl(bookAPI)
    }
}