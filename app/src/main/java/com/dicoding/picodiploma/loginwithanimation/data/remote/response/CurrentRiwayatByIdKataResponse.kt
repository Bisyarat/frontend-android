package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class CurrentRiwayatByIdKataResponse(
    @field:SerializedName("data")
    val currentRiwayatByIdKataItem: CurrentRiwayatByIdKataItem? = null,

    @field:SerializedName("errors")
    val errors: String,
)

data class CurrentRiwayatByIdKataItem(
    @field:SerializedName("id")
    val idKata: Int? = null,

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("url_video")
    val urlVideo: String? = null,

    @field:SerializedName("kata")
    val kata: String? = null,
)
