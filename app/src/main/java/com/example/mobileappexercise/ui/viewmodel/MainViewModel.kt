package com.example.mobileappexercise.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappexercise.data.Photo
import com.example.mobileappexercise.repository.FlickrRepository
import com.example.mobileappexercise.util.LoadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val flickrRepository: FlickrRepository
) : ViewModel() {

    private var _searchTerm = ""
    val searchTerm: String get() = _searchTerm

    private var _currentPage = 0
    val currentPage: Int get() = _currentPage

    private val _photoList = MutableLiveData<ArrayList<Photo>>()
    val photoList: LiveData<ArrayList<Photo>> get() = _photoList

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus> get() = _loadingStatus

    private var job: Job? = null

    init {
        reset()
    }

    // this is the function responsible for
    // requesting from the API
    // coroutines ensure that this will be run on a proper thread
    fun fetchFlickrPhotos(text: String) {
        _searchTerm = text
        _loadingStatus.value = LoadingStatus.LOADING

        job?.cancel()
        job = viewModelScope.launch {
            val response = flickrRepository.fetchFlickrPhotos(text, (_currentPage + 1))

            if (response.isSuccessful) {
                response.body()?.let { flickrResponse ->
                    if (flickrResponse.stat.equals("ok", ignoreCase = true) && flickrResponse.photos != null) {
                        _photoList.value!!.addAll(flickrResponse.photos!!.photo)
                        _photoList.value = ArrayList(_photoList.value!!.distinct())
                        _currentPage += 1
                        _loadingStatus.value = LoadingStatus.SUCCESS

                        return@launch
                    }
                }
            }

            _photoList.value = ArrayList()
            _loadingStatus.value = LoadingStatus.FAIL
        }
    }

    fun onLoadMore() {
        if (_loadingStatus.value != LoadingStatus.LOADING) {
            fetchFlickrPhotos(_searchTerm)
        }
    }

    fun reset() {
        job?.cancel()

        _searchTerm = ""
        _photoList.value = ArrayList()
        _currentPage = 0
        _loadingStatus.value = LoadingStatus.SUCCESS
    }
}