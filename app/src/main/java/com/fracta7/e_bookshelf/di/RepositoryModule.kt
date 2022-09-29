package com.fracta7.e_bookshelf.di

import com.fracta7.e_bookshelf.data.repository.AppRepositoryImpl
import com.fracta7.e_bookshelf.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun repositoryBinder(repositoryImpl: AppRepositoryImpl): AppRepository
}