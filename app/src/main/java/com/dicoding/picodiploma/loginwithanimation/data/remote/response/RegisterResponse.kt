package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("errors")
    val errors: String,

    @field:SerializedName("data")
    val dataResult: DataResult,
)

data class DataResult(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("name")
    val name: String
)
