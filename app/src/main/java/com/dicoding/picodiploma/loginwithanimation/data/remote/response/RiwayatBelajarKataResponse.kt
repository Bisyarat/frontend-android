package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class CurrentRiwayatBelajarResponse (
    @field:SerializedName("data")
    val riwayatBelajar: List<RiwayatBelajar> = emptyList(),
)

data class RiwayatBelajarKataResponse (
    @field:SerializedName("data")
    val riwayatBelajar: RiwayatBelajar? = null,

    @field:SerializedName("errors")
    val errors: String,
)

data class RiwayatBelajar (
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("url_video")
    val urlVideo: String? = null,

    @field:SerializedName("id_kata")
    val idKata: Int? = null,
)