package com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit

import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.loginwithanimation.data.RiwayatBelajarRequest
import com.dicoding.picodiploma.loginwithanimation.data.UserLogin
import com.dicoding.picodiploma.loginwithanimation.data.UserRegister
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.AddNewStoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.CurrentRiwayatByIdKataResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.CurrentUserResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.KataByIdResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.KataResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.KategoriResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.LogoutResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.RiwayatBelajarKataResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.StoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.SubKategoriResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/users")
    suspend fun register(
       @Body userRegister: UserRegister
    ): RegisterResponse

    @POST("api/users/login")
    suspend fun login(
        @Body userLogin: UserLogin
    ): LoginResponse

    @GET("api/users/current")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String,
    ): CurrentUserResponse

    @GET("api/kategori")
    suspend fun getAllKategori(
        @Header("Authorization") token: String,
    ): KategoriResponse

    @GET("api/subkategori")
    suspend fun getAllSubKategori(
        @Header("Authorization") token: String,
    ): SubKategoriResponse

    @GET("api/status/kata")
    suspend fun getAllKata(
        @Header("Authorization") token: String,
        @Query("nama_kategori") namaKategori: String? = null,
        @Query("nama_sub_kategori ") namaSubKategori: String? = null,
    ): KataResponse

    @DELETE("api/users/logout")
    suspend fun deleteLogoutUser(
        @Header("Authorization") token: String,
    ): LogoutResponse

    @GET("api/kata/{id}")
    suspend fun getKataById(
        @Path("id") id: Int
    ): KataByIdResponse

    @POST("api/riwayat")
    suspend fun createRiwayatBelajar(
        @Header("Authorization") token: String,
        @Body riwayatBelajarRequest: RiwayatBelajarRequest
    ): RiwayatBelajarKataResponse

    @GET("api/riwayat/{id}")
    suspend fun getCurrentStatusKataById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): CurrentRiwayatByIdKataResponse

    //hapus
    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun addNewStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): AddNewStoryResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1,
    ): StoryResponse
    //end hapus


}