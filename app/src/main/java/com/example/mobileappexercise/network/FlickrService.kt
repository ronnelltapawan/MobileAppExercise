@file:Suppress("SpellCheckingInspection")

package com.example.mobileappexercise.network

import com.example.mobileappexercise.data.Flickr
import com.example.mobileappexercise.util.API_KEY
import com.example.mobileappexercise.util.FORMAT
import com.example.mobileappexercise.util.NO_JSON_CALLBACK
import com.example.mobileappexercise.util.PAGE_SIZE
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Query

interface FlickrService {

    @GET(
        "?method=flickr.photos.search" +
                "&api_key=$API_KEY" +
                "&format=$FORMAT" +
                "&nojsoncallback=$NO_JSON_CALLBACK" +
                "&per_page=$PAGE_SIZE"
    )
    suspend fun fetchFlickrPhotos(
        @Query("text") text: String,
        @Query("page") page: Int
    ): Response<Flickr>
}