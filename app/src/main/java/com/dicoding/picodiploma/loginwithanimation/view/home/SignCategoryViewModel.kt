package com.dicoding.picodiploma.loginwithanimation.view.home

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.SignCategory
import com.dicoding.picodiploma.loginwithanimation.data.SignCategoryRepository

class SignCategoryViewModel(private val signCategoryRepository: SignCategoryRepository): ViewModel() {

    fun getListCourseSignCategory() : List<SignCategory> {
        return signCategoryRepository.getListCourseSignCategory()
    }
}