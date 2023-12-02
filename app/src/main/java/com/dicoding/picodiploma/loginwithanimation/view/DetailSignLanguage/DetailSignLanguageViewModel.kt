package com.dicoding.picodiploma.loginwithanimation.view.DetailSignLanguage

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.SignCategory
import com.dicoding.picodiploma.loginwithanimation.data.SignCategoryRepository

class DetailSignLanguageViewModel(private val signCategoryRepository: SignCategoryRepository): ViewModel() {
    fun getListCourseSignCategory() : List<SignCategory> {
        return signCategoryRepository.getListCourseSignCategory()
    }
}