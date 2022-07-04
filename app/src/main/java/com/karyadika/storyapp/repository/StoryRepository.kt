package com.karyadika.storyapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.karyadika.storyapp.data.StoryRemoteMediator
import com.karyadika.storyapp.data.local.room.StoryDatabase
import com.karyadika.storyapp.data.local.room.StoryModel
import com.karyadika.storyapp.data.remote.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(private val apiService: ApiService, private val storyDatabase: StoryDatabase) {

    @OptIn(ExperimentalPagingApi::class)
    fun fetchAllStory(token: String): LiveData<PagingData<StoryModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = StoryRemoteMediator(token, apiService, storyDatabase),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }).liveData
    }


    suspend fun fetchAllStoryWithLocation(token: String) =
        apiService.fetchStories("Bearer $token", size = 20, location = 1)


    suspend fun uploadStory(token: String, file: MultipartBody.Part, description: RequestBody, lat: RequestBody? = null,
                            lon: RequestBody? = null) =
        apiService.uploadImage("Bearer $token", file, description, lat, lon)


    companion object {
        private var INSTANCE: StoryRepository? = null
        fun getInstance(apiService: ApiService, storyDatabase: StoryDatabase): StoryRepository {
            return INSTANCE ?: synchronized(this) {
                StoryRepository(apiService, storyDatabase).also {
                    INSTANCE = it
                }
            }
        }
    }

}