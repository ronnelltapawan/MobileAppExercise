package com.example.mobileappexercise.data

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id") var id: String? = null,
    @SerializedName("owner") var owner: String? = null,
    @SerializedName("secret") var secret: String? = null,
    @SerializedName("server") var server: String? = null,
    @SerializedName("farm") var farm: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("ispublic") var isPublic: Int? = null,
    @SerializedName("isfriend") var isFriend: Int? = null,
    @SerializedName("isfamily") var isFamily: Int? = null
)