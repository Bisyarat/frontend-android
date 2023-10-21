package com.dicoding.picodiploma.loginwithanimation.view.detailStory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailStory(
    val photoUrl: String,
    val name: String,
    val description: String,
) : Parcelable
