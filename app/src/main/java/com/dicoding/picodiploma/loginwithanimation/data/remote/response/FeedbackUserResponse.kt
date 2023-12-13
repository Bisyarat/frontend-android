package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class FeedbackUserResponse(
    @field:SerializedName("keterangan")
    val keterangan: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,
)
