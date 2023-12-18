package com.dicoding.picodiploma.loginwithanimation.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.data.SignCategoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.di.Injection
import com.dicoding.picodiploma.loginwithanimation.view.DetailExercise.DetailExerciseViewModel
import com.dicoding.picodiploma.loginwithanimation.view.DetailSignLanguage.DetailSignLanguageViewModel
import com.dicoding.picodiploma.loginwithanimation.view.DetailSignWordCategory.DetailSignWordCategoryViewModel
import com.dicoding.picodiploma.loginwithanimation.view.addStory.AddStoryViewModel
import com.dicoding.picodiploma.loginwithanimation.view.home.SignCategoryViewModel
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginViewModel
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel
import com.dicoding.picodiploma.loginwithanimation.view.signup.SignupViewModel

class ViewModelFactory(private val repository: UserRepository, private val signCategoryRepository: SignCategoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignCategoryViewModel::class.java) -> {
                SignCategoryViewModel(signCategoryRepository) as T
            }
            modelClass.isAssignableFrom(DetailSignWordCategoryViewModel::class.java) -> {
                DetailSignWordCategoryViewModel(signCategoryRepository) as T
            }
            modelClass.isAssignableFrom(DetailSignLanguageViewModel::class.java) -> {
                DetailSignLanguageViewModel(signCategoryRepository) as T
            }
            modelClass.isAssignableFrom(DetailExerciseViewModel::class.java) -> {
                DetailExerciseViewModel(signCategoryRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context), Injection.provideSignCategoryRepository())
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}