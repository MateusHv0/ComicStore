package com.example.comicstore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.comicstore.dao.DetailsDao
import com.example.comicstore.dao.ComicDao
import com.example.comicstore.dao.ImagesDao
import com.example.comicstore.data.Details
import com.example.comicstore.data.Image
import com.example.comicstore.data.Comic

@Database(
    entities = [Comic::class, Details::class, Image::class],
    version = 4
)

abstract class ComicDataBase: RoomDatabase() {
    abstract fun detailsDao(comicDatabase: ComicDataBase): DetailsDao
    abstract fun comicDao(comicDatabase: ComicDataBase): ComicDao
    abstract fun imagesDao(): ImagesDao
}