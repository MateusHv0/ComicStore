package com.example.comicstore.datasource

import com.example.comicstore.dao.DetailsDao
import com.example.comicstore.dao.ComicDao
import com.example.comicstore.dao.ImagesDao
import com.example.comicstore.data.Details
import com.example.comicstore.data.Comic
import com.example.comicstore.data.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicDatabaseDataSource @Inject constructor(
    private var comicDao: ComicDao,
    private var detailsDao: DetailsDao,
    private var imageDao: ImagesDao,
    ) : ComicDataSource {

    override suspend fun getComicData(): Result<List<Comic>?> =
        withContext(Dispatchers.IO) {
            try {
                val comicList = comicDao.getAllComics()
                Result.success(comicList)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getMovieImage(comicId: Int): Result<List<Image>?> =
        withContext(Dispatchers.IO) {
            try {
                val details = imageDao.getAllImages()
                Result.success(details)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }


    override suspend fun getComicDetails(comicId: Int): Result<Details?> =
        withContext(Dispatchers.IO) {
            try {
                val details = detailsDao.getDetails(comicId)
                Result.success(details)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun saveComicData(comicList: List<Comic>) {
        comicDao.insertComicList(comicList)
    }

    override suspend fun saveDetailsData(details: Details, comicId: Int) {
        val detailsData = Details()
        detailsData.name = details.name
        detailsData.description = details.description
        detailsData.banner = details.banner
        detailsData.comicId = comicId
        detailsDao.insertDetails(detailsData)
    }

    override suspend fun saveImageData(image: Image, comicId: Int) {
        image.comicId = comicId
        imageDao.insertImage(image)
    }

    override suspend fun clearComicData() {
        comicDao.clearComicsData()
    }

    override suspend fun clearDetailsData() {
        detailsDao.clearDetailsData()
    }

    override suspend fun clearImagesData() {
        imageDao.clearImagesData()
    }

}
