package com.dicoding.picodiploma.loginwithanimation.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.StoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {
    //From API
    fun register(user: UserModel) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.register(user.name!!, user.email!!, user.password!!)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(ResultState.Error(errorResponse.message))
        }
    }

    fun login(user: UserModel) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.login(user.email!!, user.password!!)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(ResultState.Error(errorResponse.message!!))
        }
    }

    fun getStories(token: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getStories("Bearer " + token)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            emit(ResultState.Error(errorResponse.message!!))
        }
    }

    fun getQuote(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                QuotePagingSource(apiService, "Bearer " + token)
            }
        ).liveData
    }

    fun getStoriesWithLocation(token: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getStoriesWithLocation("Bearer " + token)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            emit(ResultState.Error(errorResponse.message!!))
        }
    }

    fun addNewStory(token: String, file: MultipartBody.Part, description: RequestBody) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.addNewStory("Bearer " + token, file, description)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            emit(ResultState.Error(errorResponse.message!!))
        }
    }

    //From Shared Preference
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}