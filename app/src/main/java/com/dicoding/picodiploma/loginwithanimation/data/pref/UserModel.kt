package com.dicoding.picodiploma.loginwithanimation.data.pref

data class UserModel(
    val email: String? = null,
    val token: String,
    val isLogin: Boolean = false,
    val name: String? = null,
    val password: String? = null,
)