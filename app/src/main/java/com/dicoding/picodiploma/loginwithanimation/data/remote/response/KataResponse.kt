package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class KataResponse (
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("id_kategori")
    val idKategori: Int? = null,

    @field:SerializedName("id_video")
    val idVideo: Int? = null,

    @field:SerializedName("id_sub_kategori")
    val idSubKategori: Int? = null,

    @field:SerializedName("kata")
    val kata: String? = null,

    @field:SerializedName("url_video")
    val urlVideo: String? = null,

    @field:SerializedName("penjelasan")
    val penjelasan: String? = null,
)
