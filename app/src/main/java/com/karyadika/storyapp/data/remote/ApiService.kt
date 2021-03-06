package com.karyadika.storyapp.data.remote

import com.karyadika.storyapp.data.remote.stories.UploadResponse
import com.karyadika.storyapp.data.remote.user.login.LoginRequest
import com.karyadika.storyapp.data.remote.user.login.LoginResponse
import com.karyadika.storyapp.data.remote.user.register.RegisterRequest
import com.karyadika.storyapp.data.remote.user.register.RegisterResponse
import com.karyadika.storyapp.data.remote.stories.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>


    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>



    @GET("stories")
    suspend fun fetchStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null
    ): Response<StoryResponse>


    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part ("description") description: RequestBody,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?
    ): Response<UploadResponse>

}