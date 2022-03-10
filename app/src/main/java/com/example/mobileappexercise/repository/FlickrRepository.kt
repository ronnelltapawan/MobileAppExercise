package com.example.mobileappexercise.repository

import com.example.mobileappexercise.network.FlickrService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FlickrRepository @Inject constructor(private val flickrService: FlickrService) {

    suspend fun fetchFlickrPhotos(text: String, page: Int) = withContext(Dispatchers.IO) {
        flickrService.fetchFlickrPhotos(text, page)
    }
}