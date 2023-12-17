package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class SubKategoriResponse (
    @field:SerializedName("data")
    val subKategoriItem: List<SubKategoriItem> = emptyList(),

    @field:SerializedName("errors")
    val errors: String
)

data class SubKategoriItem (
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama_sub_kategori")
    val namaKategori: String,

    @field:SerializedName("penjelasan")
    val penjelasan: String? = null,

)
