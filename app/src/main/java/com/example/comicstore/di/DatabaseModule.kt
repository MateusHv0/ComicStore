package com.example.comicstore.di

import android.content.Context
import androidx.room.Room
import com.example.comicstore.dao.DetailsDao
import com.example.comicstore.dao.ComicDao
import com.example.comicstore.dao.ImagesDao
import com.example.comicstore.database.ComicDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context):ComicDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            ComicDataBase::class.java,
            "movie_data_base"
        )   .fallbackToDestructiveMigration()
            .build()
    @Provides
    fun provideComicDao(comicDb: ComicDataBase): ComicDao =
        comicDb.comicDao(comicDb)
    @Provides
    fun provideImageDao(comicDb: ComicDataBase): ImagesDao =
        comicDb.imagesDao()
    @Provides
    fun provideDetailsDao(comicDb: ComicDataBase): DetailsDao =
        comicDb.detailsDao(comicDb)

}