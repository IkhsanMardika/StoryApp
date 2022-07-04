package com.karyadika.storyapp.ui.user.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karyadika.storyapp.repository.UserRepository
import com.karyadika.storyapp.data.remote.user.register.RegisterRequest
import com.karyadika.storyapp.data.remote.user.register.RegisterResponse
import com.karyadika.storyapp.utils.Result
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    private var _registerResponse = MutableLiveData<Result<RegisterResponse>>()
    val registerResponse: LiveData<Result<RegisterResponse>> = _registerResponse


    fun register(registerRequest: RegisterRequest) = viewModelScope.launch {
        _registerResponse.value = Result.Loading()
        try {
            val response = repository.register(registerRequest)
            _registerResponse.value = Result.Success(response.body()!!)
        } catch (e: Exception) {
            _registerResponse.value = Result.Error(e.message)
            Log.e("RegisterViewModel",e.message.toString())
        }
    }

}



