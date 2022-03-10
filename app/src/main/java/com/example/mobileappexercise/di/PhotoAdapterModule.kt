package com.example.mobileappexercise.di

import androidx.fragment.app.Fragment
import com.example.mobileappexercise.ui.adapter.PhotoAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class PhotoAdapterModule {

    @Provides
    fun provideCustomCallback(fragment: Fragment): PhotoAdapter.CustomCallback =
        fragment as PhotoAdapter.CustomCallback
}