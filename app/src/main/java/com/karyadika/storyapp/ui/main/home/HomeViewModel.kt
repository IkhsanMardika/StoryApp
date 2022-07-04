package com.karyadika.storyapp.ui.main.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.karyadika.storyapp.data.local.room.StoryModel
import com.karyadika.storyapp.repository.StoryRepository
import com.karyadika.storyapp.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {

    fun fetchAllStory(token: String): LiveData<PagingData<StoryModel>> =
        storyRepository.fetchAllStory(token).cachedIn(viewModelScope)

    fun deleteUser() = viewModelScope.launch {
        userRepository.deleteUser()
    }

}