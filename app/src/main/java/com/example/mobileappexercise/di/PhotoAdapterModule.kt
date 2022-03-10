package com.example.mobileappexercise.di

import android.app.Activity
import com.example.mobileappexercise.ui.adapter.PhotoAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class PhotoAdapterModule {

    @Provides
    fun provideCustomCallback(activity: Activity): PhotoAdapter.CustomCallback =
        activity as PhotoAdapter.CustomCallback
}