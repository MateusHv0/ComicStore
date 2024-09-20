package com.example.comicstore.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.comicstore.data.Comic

@Dao
abstract class ComicDao: BaseDao<Comic> {
    @Transaction
    @Query("SELECT * FROM comic")
    abstract suspend fun getAllComics(): List<Comic>?

    @Transaction
    @Query("SELECT * FROM comic WHERE id=:comicId")
    abstract suspend fun getComic(comicId: Int): Comic?
    @Transaction
    open suspend fun insertComic(comic: Comic){
         insert(comic)
    }
    @Transaction
    @Query("DELETE from comic")
    abstract suspend fun clearComicsData()

    @Transaction
    open suspend fun insertComicList(comicList: List<Comic>){
        comicList.forEach{insertComic(it)}
    }
}