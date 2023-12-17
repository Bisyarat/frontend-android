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
