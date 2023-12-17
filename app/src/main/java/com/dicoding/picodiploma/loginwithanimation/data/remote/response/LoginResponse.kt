package com.dicoding.picodiploma.loginwithanimation.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val dataResult: DataLoginResult,

    @field:SerializedName("errors")
    val errors: String
)

data class DataLoginResult(
    @field:SerializedName("token")
    val token: String
)

data class CurrentUserResponse(
    @field:SerializedName("data")
    val dataCurrentUserResult: DataCurrentUserResult,

    @field:SerializedName("errors")
    val errors: String
)

data class DataCurrentUserResult(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("name")
    val name: String,
)
