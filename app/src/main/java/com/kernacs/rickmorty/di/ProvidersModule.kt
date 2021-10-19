package com.kernacs.rickmorty.di

import android.content.Context
import com.kernacs.rickmorty.BuildConfig
import com.kernacs.rickmorty.data.RickAndMortyDatabase
import com.kernacs.rickmorty.data.local.dao.CharactersDao
import com.kernacs.rickmorty.data.local.dao.EpisodesDao
import com.kernacs.rickmorty.data.local.dao.FavouritesDao
import com.kernacs.rickmorty.data.remote.api.RickAndMortyApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvidersModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(RickAndMortyApiInterface.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RickAndMortyApiInterface = retrofit.create(
        RickAndMortyApiInterface::class.java
    )

    @Provides
    fun providesIoCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        RickAndMortyDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharactersDao(db: RickAndMortyDatabase): CharactersDao = db.charactersDao()

    @Singleton
    @Provides
    fun provideEpisodesDao(db: RickAndMortyDatabase): EpisodesDao = db.episodesDao()

    @Singleton
    @Provides
    fun provideFavouritesDao(db: RickAndMortyDatabase): FavouritesDao = db.favouritesDao()
}