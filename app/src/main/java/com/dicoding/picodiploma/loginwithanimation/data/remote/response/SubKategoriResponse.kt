package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName


data class SubKategoriResponse (
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("id_kategori")
    val idKategori: Int? = null,

    @field:SerializedName("nama_sub_kategori")
    val namaSubKategori: String? = null,
)
