package com.dicoding.picodiploma.loginwithanimation.view.map

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository

class MapViewModel(private val repository: UserRepository) : ViewModel() {
    fun getStoriesWithLocation(token: String) = repository.getStoriesWithLocation(token)
}