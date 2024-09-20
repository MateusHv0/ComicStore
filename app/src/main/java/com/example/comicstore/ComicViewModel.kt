package com.example.comicstore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicstore.data.Details
import com.example.comicstore.data.Comic
import com.example.comicstore.data.Image
import com.example.comicstore.data.ImageResponse
import com.example.comicstore.helper.Events
import com.example.comicstore.helper.States
import com.example.comicstore.repository.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class ComicViewModel @Inject constructor(
    val comicRepository : ComicRepository
): ViewModel(){
    private val _comicDetailsLivedata = MutableLiveData<Details?>()
    private val _comicListLivedata = MutableLiveData<List<Comic>?>()
    private val _imageLiveData = MutableLiveData<Image?>()
    private val _navigationToDetailLiveData = MutableLiveData<Events<Unit>>()
    private val _appState = MutableLiveData<States>()
    val comicDetailsLiveData: LiveData<Details?>
        get() = _comicDetailsLivedata

    val comicListLivedata: LiveData<List<Comic>?>
        get() = _comicListLivedata

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData

    val imageLiveData
        get() = _imageLiveData

    val appState: LiveData<States>
        get() = _appState

    fun onComicSelected(position: Int) {
        val comicID = _comicListLivedata.value?.get(position)?.id
        comicID?.let {
            getDetails(it)
            getImage(it)
        }
        _navigationToDetailLiveData.postValue(Events(Unit))
    }

    init {
        _appState.postValue(States.LOADING)
        getComic()
    }
    // Resultados do repositorio
    @VisibleForTesting
    fun getComic() {
        _appState.postValue(States.LOADING)
        viewModelScope.launch {
            val comicListResult = comicRepository.getComicData()
            comicListResult.fold(
                onSuccess = {
                    _comicListLivedata.value = it
                    _appState.value = States.SUCCESS
                },
                onFailure = { _appState.value = States.ERROR }
            )
        }
    }
    @VisibleForTesting
    fun getDetails(id: Int) {
        _appState.postValue(States.LOADING)
        viewModelScope.launch {
            val detailListResult = comicRepository.getComicDetails(id)
            detailListResult.fold(
                onSuccess = {
                    _comicDetailsLivedata.value = it
                    _appState.value = States.SUCCESS
                },
                onFailure = { _appState.value = States.ERROR }
            )
        }
    }
    @VisibleForTesting
    fun getImage(id: Int) {
        _appState.postValue(States.LOADING)
        viewModelScope.launch {
            val imageListResult = comicRepository.getImageData(id)
            imageListResult.fold(
                onSuccess = {
                    _imageLiveData.value = it
                    _appState.value = States.SUCCESS
                },
                onFailure = { _appState.value = States.ERROR }
            )
        }
    }
}
