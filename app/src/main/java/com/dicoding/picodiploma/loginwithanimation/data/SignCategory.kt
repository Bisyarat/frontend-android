package com.dicoding.picodiploma.loginwithanimation.data

data class SignCategory(
    val idKata: Int? = null,
    val idPhoto: Int? = null,
    val photoUrl: String,
    val titleCategory: String,
    val progressCategory: Int,
    val isChecked: Boolean? = null
)