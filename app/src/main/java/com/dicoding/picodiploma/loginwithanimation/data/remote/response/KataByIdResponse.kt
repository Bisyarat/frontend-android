package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class KataByIdResponse(
    @field:SerializedName("data")
    val kataByIdItem: KataByIdItem
)

data class KataByIdItem(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("kata")
    val kata: String? = null,

    @field:SerializedName("url_video")
    val urlVideo:  String? = null,

    @field:SerializedName("penjelasan")
    val penjelasan:  String? = null,

    @field:SerializedName("nama_kategori")
    val namaKategori:  String? = null,

    @field:SerializedName("nama_sub_kategori")
    val namaSubKategori:  String? = null,
)