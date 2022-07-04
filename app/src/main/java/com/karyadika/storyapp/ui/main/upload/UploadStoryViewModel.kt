package com.karyadika.storyapp.ui.main.upload

import androidx.lifecycle.*
import com.karyadika.storyapp.data.remote.stories.UploadResponse
import com.karyadika.storyapp.repository.StoryRepository
import com.karyadika.storyapp.repository.UserRepository
import com.karyadika.storyapp.utils.Result
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadStoryViewModel(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository
    ) : ViewModel() {

    private val _uploadResponse = MutableLiveData<Result<UploadResponse>>()
    val uploadResponse: LiveData<Result<UploadResponse>> = _uploadResponse


    fun uploadStory(token: String,
                    file: MultipartBody.Part,
                    description: RequestBody,
                    lat: RequestBody?,
                    lon: RequestBody?) = viewModelScope.launch {
        _uploadResponse.value = Result.Loading()
        try {
            val response = storyRepository.uploadStory(token, file, description, lat, lon)
            _uploadResponse.value = Result.Success(response.body()!!)
        } catch (e: Exception) {
            _uploadResponse.value = Result.Error(e.message.toString())
        }
    }



    fun fetchUser(): LiveData<String> {
        return userRepository.fetchUser().asLiveData()
    }

}