package com.dicoding.picodiploma.loginwithanimation.view.DetailExercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.picodiploma.loginwithanimation.data.RiwayatBelajarRequest
import com.dicoding.picodiploma.loginwithanimation.data.SignCategoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DetailExerciseViewModel(private val signCategoryRepository: SignCategoryRepository) :
    ViewModel() {

    fun getKataById(id: Int) = signCategoryRepository.getKataById(id)
    fun createRiwayatBelajar(token: String, status: Boolean, url_video: String, id_kata: Int) =
        signCategoryRepository.createRiwayatBelajar(
            token,
            status, url_video, id_kata
        )

    fun getCurrentStatusKataById(token: String, id: Int) =
        signCategoryRepository.getCurrentStatusKataById(token, id)
}