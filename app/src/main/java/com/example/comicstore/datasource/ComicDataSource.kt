package com.example.comicstore.datasource

import com.example.comicstore.data.Details
import com.example.comicstore.data.Comic
import com.example.comicstore.data.Image

interface ComicDataSource{
    suspend fun getComicData() : Result<List<Comic>?>
    suspend fun getMovieImage(comicId: Int) : Result<List<Image>?>
    suspend fun getComicDetails(comicId: Int) : Result<Details?>
    suspend fun saveComicData(comicList: List<Comic>)
    suspend fun saveDetailsData(details: Details, comicId: Int)
    suspend fun saveImageData(image: Image, comicId: Int)
    suspend fun clearComicData()
    suspend fun clearDetailsData()
    suspend fun clearImagesData()
}