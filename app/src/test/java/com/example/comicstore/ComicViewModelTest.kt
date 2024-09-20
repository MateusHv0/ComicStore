package com.example.comicstore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.comicstore.data.Details
import com.example.comicstore.data.Comic
import com.example.comicstore.helper.States
import com.example.comicstore.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat


@ExperimentalCoroutinesApi
class ComicViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    val dispatcher = UnconfinedTestDispatcher()

    val movieRepository: MovieRepository = mockk()
    val appStateObserver: Observer<States> = mockk(relaxed = true)
    val appStateValues = mutableListOf<States>()

    lateinit var comicViewModel: ComicViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxed = true)

        justRun {
            appStateObserver.onChanged(capture(appStateValues))
        }

        coEvery {
            movieRepository.getMovieData()
            movieRepository.getDetailsData(any())
            movieRepository.getImageData(any())
        } returns Result.failure(Throwable("test"))



        comicViewModel = ComicViewModel(movieRepository)

        comicViewModel.appState.observeForever(appStateObserver)
        appStateValues.clear()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        comicViewModel.appState.removeObserver(appStateObserver)
        appStateValues.clear()
    }

    //API Data States When Has Success

    @Test
    fun getMovieData_whenMovieRepository_hasData_shouldChangeStateToSuccess() = runBlocking {
        //Configuration
        coEvery { movieRepository.getMovieData() } returns Result.success(listOf(Comic()))

        //Execution
        comicViewModel.getMovie()

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.SUCCESS))
    }
    @Test
    fun getDetailsData_whenMovieRepository_hasData_shouldChangeStateToSuccess() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        coEvery { movieRepository.getDetailsData(id) } returns Result.success(Details())

        //Execution
        comicViewModel.getDetails(id)

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.SUCCESS))
    }
    @Test
    fun getImageData_whenMovieRepository_hasData_shouldChangeStateToSuccess() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        coEvery { movieRepository.getImageData(id) } returns Result.success(ImageResponse())

        //Execution
        comicViewModel.getImage(id)

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.SUCCESS))
    }

    //API Data States When Has Failure

    @Test
    fun getMovieData_whenMovieRepository_hasError_shouldChangeStateToError() = runBlocking {
        //Configuration
        coEvery { movieRepository.getMovieData() } returns Result.failure(Throwable("test"))

        //Execution
        comicViewModel.getMovie()

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.ERROR))
    }
    @Test
    fun getDetailsData_whenMovieRepository_hasError_shouldChangeStateToError() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        coEvery { movieRepository.getDetailsData(id) } returns Result.failure(Throwable("test"))

        //Execution
        comicViewModel.getDetails(id)

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.ERROR))
    }
    @Test
    fun getImageData_whenMovieRepository_hasError_shouldChangeStateToError() = runBlocking {
        //Configuration
        val id = 3 // Only for tests
        coEvery { movieRepository.getImageData(id) } returns Result.failure(Throwable("test"))

        //Execution
        comicViewModel.getImage(id)

        //Verify
        assertThat(appStateValues).isEqualTo(listOf(States.LOADING, States.ERROR))
    }

    //API Check Data And Emit

    @Test
    fun getMovieData_whenMovieRepository_hasData_shouldChangeEmitList() = runBlocking {
        //Configuration
        val list = listOf(Comic())
        coEvery { movieRepository.getMovieData() } returns Result.success(list)

        //Execution
        comicViewModel.getMovie()

        //Verify
        assertThat(comicViewModel.movieListLivedata.value).isEqualTo(list)
    }
    @Test
    fun getDetailsData_whenMovieRepository_hasData_shouldChangeEmit() = runBlocking {
        //Configuration
        val details = Details()
        val id = 3 // Only for tests
        coEvery { movieRepository.getDetailsData(id) } returns Result.success(details)

        //Execution
        comicViewModel.getDetails(id)

        //Verify
        assertThat(comicViewModel.movieDetailsLiveData.value).isEqualTo(details)
    }
    @Test
    fun getImagesData_whenMovieRepository_hasData_shouldChangeEmit() = runBlocking {
        //Configuration
        val image = ImageResponse()
        val id = 3 // Only for tests
        coEvery { movieRepository.getImageData(id) } returns Result.success(image)

        //Execution
        comicViewModel.getImage(id)

        //Verify
        assertThat(comicViewModel.imageLiveData.value).isEqualTo(image)
    }

    //API Check Data And Don't Emit When Has Error

    @Test
    fun getMovieData_whenMovieRepository_hasError_shouldNotEmitList() = runBlocking {
        //Configuration
        val list = listOf(Comic())
        coEvery { movieRepository.getMovieData() } returns Result.failure(Throwable("test"))

        //Execution
        comicViewModel.getMovie()

        //Verify
        assertThat(comicViewModel.movieListLivedata.value).isNull()
    }
    @Test
    fun getDetailsData_whenMovieRepository_hasError_shouldNotEmit() = runBlocking {
        //Configuration
        val details = Details()
        val id = 3 // Only for tests
        coEvery { movieRepository.getDetailsData(id) } returns Result.failure(Throwable("test"))

        //Execution
        comicViewModel.getDetails(id)

        //Verify
        assertThat(comicViewModel.movieDetailsLiveData.value).isNull()
    }
    @Test
    fun getImagesData_whenMovieRepository_hasError_shouldNotEmit() = runBlocking {
        //Configuration
        val image = ImageResponse()
        val id = 3 // Only for tests
        coEvery { movieRepository.getImageData(id) } returns Result.failure(Throwable("test"))

        //Execution
        comicViewModel.getImage(id)

        //Verify
        assertThat(comicViewModel.imageLiveData.value).isNull()
    }
}
