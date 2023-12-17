package com.dicoding.picodiploma.loginwithanimation.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.loginwithanimation.data.dataDummy.FakeSignCategoryDataSource
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.KategoriResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException

class SignCategoryRepository private constructor(
    private val apiService: ApiService,
){
    private val signCategory = mutableListOf<SignCategory>()
    init {
        if (signCategory.isEmpty()){
            FakeSignCategoryDataSource.dummySignCategories.forEach{
                signCategory.add(SignCategory(it.idPhoto, it.photoUrl, it.titleCategory, it.progressCategory))
            }
        }
    }

    fun getListCourseSignCategory() : List<SignCategory> {
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