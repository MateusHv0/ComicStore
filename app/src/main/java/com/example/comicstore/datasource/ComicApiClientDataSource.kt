package com.example.comicstore.datasource

import com.example.comicstore.api.Credentials
import com.example.comicstore.api.ComicService
import com.example.comicstore.data.Details
import com.example.comicstore.data.Comic
import com.example.comicstore.data.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicApiClientDataSource @Inject constructor(
    private var ComicService: ComicService
) : ComicDataSource {


    // API Responses
    override suspend fun getComicData(): Result<List<Comic>?> =
        withContext(Dispatchers.IO) {
            val request = ComicService.getComic(Credentials.token)
            when {
                request.isSuccessful -> Result.success(request.body()?.results)
                else -> Result.failure(Throwable(request.message()))
            }
        }

    override suspend fun getMovieImage(comicId: Int): Result<List<Image>?> =
        withContext(Dispatchers.IO) {
            val request = ComicService.getImages(comicId, Credentials.token)
            when {
                request.isSuccessful -> Result.success(request.body()?.results)
                else -> Result.failure(Throwable(request.message()))
            }
        }


    override suspend fun getComicDetails(comicId: Int): Result<Details?> =
        withContext(Dispatchers.IO) {
            val request = ComicService.getDetails(comicId, Credentials.token)
            when {
                request.isSuccessful -> Result.success(request.body())
                else -> Result.failure(Throwable(request.message()))
            }
        }

    // NO-OP
    override suspend fun saveComicData(comicList: List<Comic>) {
        // NO-OP
    }

    override suspend fun saveDetailsData(details: Details, comicId: Int) {
        // NO-OP
    }

    override suspend fun saveImageData(image: Image, comicId: Int) {
        // NO-OP
    }


    override suspend fun clearComicData() {
        // NO-OP
    }

    override suspend fun clearDetailsData() {
        // NO-OP
    }

    override suspend fun clearImagesData() {
        TODO("Not yet implemented")
    }

}
