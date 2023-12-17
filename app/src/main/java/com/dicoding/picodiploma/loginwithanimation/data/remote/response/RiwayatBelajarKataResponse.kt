package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class RiwayatBelajarKataResponse (
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("id_kata")
    val idKata: Int? = null,

    @field:SerializedName("id_kategori")
    val idKategori: Int? = null,

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("url_video")
    val urlVideo: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("created_by")
    val createdBy: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("updated_by")
    val updatedBy: String? = null,
)
