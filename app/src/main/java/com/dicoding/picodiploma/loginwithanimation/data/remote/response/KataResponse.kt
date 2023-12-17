package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class KataResponse (
    @field:SerializedName("data")
    val kataItem: List<KataItem> = emptyList(),

    @field:SerializedName("errors")
    val errors: String
)

data class KataItem(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("kata")
    val kata: String? = null,

    @field:SerializedName("riwayat_belajar")
    val riwayatBelajarItem: List<RiwayatBelajarItem> = emptyList(),
)

data class RiwayatBelajarItem(
    @field:SerializedName("status")
    val status: Boolean? = null,
)
