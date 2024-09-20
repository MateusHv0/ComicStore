package com.example.comicstore

import com.example.comicstore.data.Details
import com.example.comicstore.data.Comic
import com.example.comicstore.datasource.ComicApiClientDataSource
import com.example.comicstore.datasource.ComicDatabaseDataSource
import com.example.comicstore.repository.ComicRepository
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.coVerifySequence

class ComicRepositoryTest {
    @get:Rule
    val mocKkRule = MockKRule(this)

    val ComicApiClientDataSource : ComicApiClientDataSource = mockk()
    val ComicDatabaseDataSource : ComicDatabaseDataSource = mockk()

    val repository = ComicRepository(ComicApiClientDataSource, ComicDatabaseDataSource)

    val comicList = listOf(Comic())
    val details = Details()
    val image = ImageResponse()

    @Before
    fun setUp() {
        coEvery {
            ComicDatabaseDataSource.clearComicData()
            ComicDatabaseDataSource.clearDetailsData()
            ComicDatabaseDataSource.clearImagesData()
        }returns Unit
        coEvery {
            ComicDatabaseDataSource.saveComicData(any())
            ComicDatabaseDataSource.saveDetailsData(any(), any())
            ComicDatabaseDataSource.saveImageData(any(), any())
        }returns Unit
    }

    //Api Success

    @Test
    fun getComicData_whenApiHadSuccess_shouldPersistDataAndReturnList() = runBlocking{
        //Configuration
        val apiResponse = Result.success(comicList)
        coEvery { ComicApiClientDataSource.getComicData() } returns apiResponse

        //Execution
       val result = repository.getComicData()

        //Verify
        assertThat(result).isEqualTo(apiResponse)
        coVerifySequence {
            ComicApiClientDataSource.getComicData()
            ComicDatabaseDataSource.clearComicData()
            ComicDatabaseDataSource.saveComicData(comicList)
        }
    }
    @Test
    fun getDetailsData_whenApiHadSuccess_shouldPersistDataAndReturnList() = runBlocking{
        //Configuration
        val id = 3 // Only for tests
        val apiResponse = Result.success(details)
        coEvery { ComicApiClientDataSource.getComicDetails(id) } returns apiResponse

        //Execution
        val result = repository.getDetailsData(id)

        //Verify
        assertThat(result).isEqualTo(apiResponse)
        coVerifySequence {
            ComicApiClientDataSource.getComicDetails(id)
            ComicDatabaseDataSource.clearDetailsData()
            ComicDatabaseDataSource.saveDetailsData(details, id)
        }
    }
    @Test
    fun getImageData_whenApiHadSuccess_shouldPersistDataAndReturnList() = runBlocking{
        //Configuration
        val id = 3 // Only for tests
        val apiResponse = Result.success(image)
        coEvery { ComicApiClientDataSource.getComicImage(id) } returns apiResponse

        //Execution
        val result = repository.getImageData(id)

        //Verify
        assertThat(result).isEqualTo(apiResponse)
        coVerifySequence {
            ComicApiClientDataSource.getComicImage(id)
            ComicDatabaseDataSource.clearImagesData()
            ComicDatabaseDataSource.saveImageData(image, id)
        }
    }

    //Api Fail

    @Test
    fun getComicData_whenApiFailed_shouldLoadLocalData() = runBlocking {
        //Configuration
        val apiResponse = Result.failure<List<Comic>>(Throwable("test"))
        val localResponse = Result.success(comicList)
        coEvery { ComicApiClientDataSource.getComicData() } returns apiResponse
        coEvery { ComicDatabaseDataSource.getComicData() } returns localResponse

        //Execution
        val result = repository.getComicData()

        //Verify
       coVerify(exactly = 0){
           ComicDatabaseDataSource.clearComicData()
           ComicDatabaseDataSource.saveComicData(any())
       }
        coVerify(exactly = 1) {
           ComicDatabaseDataSource.getComicData()
        }
        assertThat(result).isEqualTo(localResponse)
    }
    @Test
    fun getDetailsData_whenApiFailed_shouldLoadLocalData() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        val apiResponse = Result.failure<Details>(Throwable("test"))
        val localResponse = Result.success(details)
        coEvery { ComicApiClientDataSource.getComicDetails(id) } returns apiResponse
        coEvery { ComicDatabaseDataSource.getComicDetails(id) } returns localResponse

        //Execution
        val result = repository.getDetailsData(id)

        //Verify
        coVerify(exactly = 0) {
            ComicDatabaseDataSource.clearDetailsData()
            ComicDatabaseDataSource.saveDetailsData(details, id)
        }
        coVerify(exactly = 1) {
            ComicDatabaseDataSource.getComicDetails(id)
        }
        assertThat(result).isEqualTo(localResponse)
    }
    @Test
    fun getImageData_whenApiFailed_shouldLoadLocalData() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        val apiResponse = Result.failure<ImageResponse>(Throwable("test"))
        val localResponse = Result.success(image)
        coEvery { ComicApiClientDataSource.getComicImage(id) } returns apiResponse
        coEvery { ComicDatabaseDataSource.getComicImage(id) } returns localResponse

        //Execution
        val result = repository.getImageData(id)

        //Verify
        coVerify(exactly = 0) {
            ComicDatabaseDataSource.clearImagesData()
            ComicDatabaseDataSource.saveImageData(image, id)
        }
        coVerify(exactly = 1) {
            ComicDatabaseDataSource.getComicImage(id)
        }
        assertThat(result).isEqualTo(localResponse)
    }

    //Api Exception

    @Test
    fun getComicData_whenExceptionOccurs_shouldLoadLocalData() = runBlocking {
        //Configuration
        val localResponse = Result.success(comicList)
        coEvery { ComicApiClientDataSource.getComicData() }.throws(Exception("test"))
        coEvery { ComicDatabaseDataSource.getComicData() } returns localResponse

        //Execution
        val result = repository.getComicData()

        //Verify
        coVerify(exactly = 0) {
            ComicDatabaseDataSource.clearComicData()
            ComicDatabaseDataSource.saveComicData(any())
        }
        coVerify(exactly = 1) {
            ComicDatabaseDataSource.getComicData()
        }
        assertThat(result).isEqualTo(localResponse)
    }

    @Test
    fun getDetailsData_whenExceptionOccurs_shouldLoadLocalData() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        val localResponse = Result.success(details)
        coEvery { ComicApiClientDataSource.getComicDetails(id) }.throws(Exception("test"))
        coEvery { ComicDatabaseDataSource.getComicDetails(id) } returns localResponse

        //Execution
        val result = repository.getDetailsData(id)

        //Verify
        coVerify(exactly = 0) {
            ComicDatabaseDataSource.clearDetailsData()
            ComicDatabaseDataSource.saveDetailsData(details, id)
        }
        coVerify(exactly = 1) {
            ComicDatabaseDataSource.getComicDetails(id)
        }
        assertThat(result).isEqualTo(localResponse)
    }
    @Test
    fun getImageData_whenExceptionOccurs_shouldLoadLocalData() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        val localResponse = Result.success(image)
        coEvery { ComicApiClientDataSource.getComicImage(id) }.throws(Exception("test"))
        coEvery { ComicDatabaseDataSource.getComicImage(id) } returns localResponse

        //Execution
        val result = repository.getImageData(id)

        //Verify
        coVerify(exactly = 0) {
            ComicDatabaseDataSource.clearImagesData()
            ComicDatabaseDataSource.saveImageData(image, id)
        }
        coVerify(exactly = 1) {
            ComicDatabaseDataSource.getComicImage(id)
        }
        assertThat(result).isEqualTo(localResponse)
    }
}