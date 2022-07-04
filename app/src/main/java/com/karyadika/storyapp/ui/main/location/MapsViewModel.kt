package com.karyadika.storyapp.ui.main.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karyadika.storyapp.utils.Result
import com.karyadika.storyapp.data.remote.stories.StoryResponse
import com.karyadika.storyapp.repository.StoryRepository
import kotlinx.coroutines.launch

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    private val _storyResponse = MutableLiveData<Result<StoryResponse>>()
    val storyResponse: LiveData<Result<StoryResponse>> = _storyResponse

    fun fetchAllStoryWithLocation(token: String) = viewModelScope.launch {
        _storyResponse.value = Result.Loading()
        try {
            val response = storyRepository.fetchAllStoryWithLocation(token)
            if (response.isSuccessful){
                _storyResponse.value = Result.Success(response.body()!!)
            }
        } catch (e: Exception) {
            _storyResponse.value = Result.Error(e.message)
        }
    }

}