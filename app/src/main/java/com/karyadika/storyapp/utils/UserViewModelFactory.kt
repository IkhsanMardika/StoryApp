package com.karyadika.storyapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karyadika.storyapp.repository.UserRepository
import com.karyadika.storyapp.di.Injection
import com.karyadika.storyapp.repository.StoryRepository
import com.karyadika.storyapp.ui.main.home.HomeViewModel
import com.karyadika.storyapp.ui.main.location.MapsViewModel
import com.karyadika.storyapp.ui.main.upload.UploadStoryViewModel
import com.karyadika.storyapp.ui.splash.SplashViewModel
import com.karyadika.storyapp.ui.user.login.LoginViewModel
import com.karyadika.storyapp.ui.user.register.RegisterViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_account")

class UserViewModelFactory(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository, storyRepository) as T
            }
            modelClass.isAssignableFrom(UploadStoryViewModel::class.java) -> {
                UploadStoryViewModel(userRepository, storyRepository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }


    companion object {
        private var INSTANCE: UserViewModelFactory? = null
        fun getInstance(context: Context): UserViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                UserViewModelFactory(
                    Injection.provideUserRepository(context.dataStore),
                    Injection.provideStoryRepository(context)).also {
                    INSTANCE = it
                }
            }
        }
    }

}