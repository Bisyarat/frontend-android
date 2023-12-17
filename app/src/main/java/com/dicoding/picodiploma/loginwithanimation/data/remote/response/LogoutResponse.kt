package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @field:SerializedName("data")
    val data: String,

    @field:SerializedName("errors")
    val errors: String
)