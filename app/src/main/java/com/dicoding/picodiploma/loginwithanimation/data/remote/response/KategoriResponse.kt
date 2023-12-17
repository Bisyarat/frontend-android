package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class KategoriResponse(
    @field:SerializedName("data")
    val listKategori: List<ListKategoriItem> = emptyList(),

    @field:SerializedName("errors")
    val errors: String
)

data class ListKategoriItem (
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama_kategori")
    val namaKategori: String? = null,

    @field:SerializedName("penjelasan")
    val penjelasan: String? = null,
)
