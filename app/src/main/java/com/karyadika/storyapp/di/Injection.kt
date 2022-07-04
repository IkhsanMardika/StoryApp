package com.karyadika.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.karyadika.storyapp.data.local.datastore.UserPreference
import com.karyadika.storyapp.data.local.room.StoryDatabase
import com.karyadika.storyapp.data.remote.ApiConfig
import com.karyadika.storyapp.repository.StoryRepository
import com.karyadika.storyapp.repository.UserRepository

object Injection {

    fun provideUserRepository(dataStore: DataStore<Preferences>): UserRepository {
        val api = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(dataStore)
        return UserRepository.getInstance(api, pref)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val api = ApiConfig.getApiService()
        val database = StoryDatabase.getDatabase(context)
        return StoryRepository(api, database)
    }

}