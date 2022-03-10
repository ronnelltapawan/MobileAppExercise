package com.example.mobileappexercise

import android.app.Application
import com.example.mobileappexercise.network.FlickrService
import com.google.gson.GsonBuilder
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class MyApp : Application()