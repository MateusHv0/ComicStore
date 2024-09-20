package com.example.comicstore.repository

import com.example.comicstore.data.Details
import com.example.comicstore.data.Comic
import com.example.comicstore.datasource.ComicApiClientDataSource
import com.example.comicstore.datasource.ComicDatabaseDataSource
import javax.inject.Inject

class ComicRepository @Inject constructor(
    private var ComicApiClientDataSource : ComicApiClientDataSource,
    private var ComicDatabaseDataSource : ComicDatabaseDataSource
) {

    // Save Data & Handlers
    suspend fun getComicData(): Result<List<Comic>?> {
        return try {
            val result = ComicApiClientDataSource.getComicData()
            if (result.isSuccess) {
                result.getOrNull()?.let { persistComicData(it) }
                result
            } else {
                getLocalComicData()
            }
        } catch (e: Exception) {
            getLocalComicData()
        }
    }

    suspend fun getComicDetails(comicId: Int): Result<Details?> {
        return try {
            val result = ComicApiClientDataSource.getComicDetails(comicId)
            if (result.isSuccess) {
                result.getOrNull()?.let { persistDetailsData(it, comicId) }
                result
            } else {
                getLocalDetailsData(comicId)
            }
        } catch (e: Exception) {
            getLocalDetailsData(comicId)
        }
    }

    suspend fun getImageData(comicId: Int): Result<ImageResponse?> {
        return try {
            val result = ComicApiClientDataSource.getComicImage(comicId)
            if (result.isSuccess) {
                result.getOrNull()?.let { persistImageData(it, comicId) }
                result
            } else {
                getLocalImageData(comicId)
            }
        } catch (e: Exception) {
            getLocalImageData(comicId)
        }
    }

    // Persist
    private suspend fun persistComicData(comicList: List<Comic>) {
        ComicDatabaseDataSource.clearComicData()
        comicList?.let { ComicDatabaseDataSource.saveComicData(it) }
    }

    private suspend fun persistDetailsData(details: Details, comicId: Int) {
        ComicDatabaseDataSource.clearDetailsData()
        val detailsData = Details()
        detailsData.title = details.title
        detailsData.overview = details.overview
        detailsData.poster_path = details.poster_path
        detailsData.comicId = comicId
        details?.let { ComicDatabaseDataSource.saveDetailsData(details, comicId) }
    }

    private suspend fun persistImageData(image: ImageResponse, comicId: Int) {
        image.comicId = comicId
        ComicDatabaseDataSource.clearImagesData()
        image?.let { ComicDatabaseDataSource.saveImageData(image, comicId) }
    }

    // Localdata
    private suspend fun getLocalComicData(): Result<List<Comic>?> =
        ComicDatabaseDataSource.getComicData()

    private suspend fun getLocalDetailsData(comicId: Int): Result<Details?> =
        ComicDatabaseDataSource.getComicDetails(comicId)

    private suspend fun getLocalImageData(comicId: Int): Result<ImageResponse?> =
        ComicDatabaseDataSource.getComicImage(comicId)
}
