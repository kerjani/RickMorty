package com.kernacs.rickmorty.di

import com.kernacs.rickmorty.data.local.LocalDataSource
import com.kernacs.rickmorty.data.local.RickAndMortyLocalDataSource
import com.kernacs.rickmorty.data.remote.RemoteDataSource
import com.kernacs.rickmorty.data.remote.RickAndMortyRemoteDataSource
import com.kernacs.rickmorty.data.repository.Repository
import com.kernacs.rickmorty.data.repository.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {
    @Binds
    abstract fun providesRemoteDataSource(
        weatherRemoteDataSourceImpl: RickAndMortyRemoteDataSource
    ): RemoteDataSource

    @Binds
    abstract fun providesRepository(
        repositoryImpl: RickAndMortyRepository
    ): Repository

    @Binds
    abstract fun providesLocalDataSource(
        repositoryImpl: RickAndMortyLocalDataSource
    ): LocalDataSource
}