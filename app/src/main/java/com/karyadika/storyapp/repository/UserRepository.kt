package com.karyadika.storyapp.repository

import com.karyadika.storyapp.data.local.datastore.UserPreference
import com.karyadika.storyapp.data.remote.ApiService
import com.karyadika.storyapp.data.remote.user.login.LoginRequest
import com.karyadika.storyapp.data.remote.user.register.RegisterRequest

class UserRepository (
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun login(loginRequest: LoginRequest) = apiService.login(loginRequest)

    suspend fun register(registerRequest: RegisterRequest) = apiService.register(registerRequest)

    suspend fun saveUser(token: String) = userPreference.saveUser(token)

    fun fetchUser() = userPreference.fetchUser()

    suspend fun deleteUser() = userPreference.deleteUser()


    companion object {
        private var INSTANCE: UserRepository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository {
            return INSTANCE ?: synchronized(this) {
                UserRepository(apiService, userPreference).also {
                    INSTANCE = it
                }
            }
        }
    }

}

