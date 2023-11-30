package com.dicoding.picodiploma.loginwithanimation.data

import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.loginwithanimation.data.dataDummy.FakeSignCategoryDataSource

class SignCategoryRepository {
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

    companion object {
        @Volatile
        private var instance: SignCategoryRepository? = null
        fun getInstance(): SignCategoryRepository =
            instance ?: synchronized(this) {
                instance ?: SignCategoryRepository()
            }.also { instance = it }
    }
}