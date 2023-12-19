package com.dicoding.picodiploma.loginwithanimation.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.loginwithanimation.data.dataDummy.FakeSignCategoryDataSource
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.KategoriResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.RiwayatBelajarKataResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.SubKategoriResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

data class RiwayatBelajarRequest(
    val status: Boolean,
    val url_video: String,
    val id_kata: Int,
)

class SignCategoryRepository private constructor(
    private val apiService: ApiService,
) {
    private val signCategory = mutableListOf<SignCategory>()
//    init {
//        if (signCategory.isEmpty()){
//            FakeSignCategoryDataSource.dummySignCategories.forEach{
//                signCategory.add(SignCategory(it.idPhoto, it.photoUrl, it.titleCategory, it.progressCategory))
//            }
//        }
//    }

    fun getListCourseSignCategory(): List<SignCategory> {
        return signCategory
    }

    fun getAllKategori(token: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getAllKategori(token)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, KategoriResponse::class.java)
            emit(ResultState.Error(errorResponse.errors!!))
        }
    }

    fun getAllSubKategori(token: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getAllSubKategori(token)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, SubKategoriResponse::class.java)
            emit(ResultState.Error(errorResponse.errors!!))
        }
    }

    fun getAllKata(token: String, namaKategori: Boolean = false, namaSubKategori: Boolean = false) =
        liveData {
            var query: String? = null
            if (namaKategori) {
                query = "Angka"
            } else if (namaSubKategori) {
                query = "Kata"
            }
            emit(ResultState.Loading)
            try {
                val successResponse = apiService.getAllKata(token, query)
                emit(ResultState.Success(successResponse))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, SubKategoriResponse::class.java)
                emit(ResultState.Error(errorResponse.errors!!))
            }
        }

    fun getKataById(id: Int) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getKataById(id)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, SubKategoriResponse::class.java)
            emit(ResultState.Error(errorResponse.errors!!))
        }
    }

    fun createRiwayatBelajar(token: String, status: Boolean, url_video: String, id_kata: Int) =
        liveData {
            emit(ResultState.Loading)
            try {
                val successResponse = apiService.createRiwayatBelajar(
                    token,
                    RiwayatBelajarRequest(status, url_video, id_kata)
                )
                emit(ResultState.Success(successResponse))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse =
                    Gson().fromJson(errorBody, RiwayatBelajarKataResponse::class.java)
                emit(ResultState.Error(errorResponse.errors!!))
            }
        }

    companion object {
        @Volatile
        private var instance: SignCategoryRepository? = null
        fun getInstance(
            apiService: ApiService
        ): SignCategoryRepository =
            instance ?: synchronized(this) {
                instance ?: SignCategoryRepository(apiService)
            }.also { instance = it }
    }
}