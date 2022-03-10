package com.example.mobileappexercise.data

import com.google.gson.annotations.SerializedName

data class Flickr(
    @SerializedName("photos") var photos: Photos? = Photos(),
    @SerializedName("stat") var stat: String = ""
)