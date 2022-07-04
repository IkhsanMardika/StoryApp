package com.karyadika.storyapp.ui.user.login

import android.util.Log
import androidx.lifecycle.*
import com.karyadika.storyapp.repository.UserRepository
import com.karyadika.storyapp.data.remote.user.login.LoginRequest
import com.karyadika.storyapp.data.remote.user.login.LoginResponse
import com.karyadika.storyapp.utils.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginResponse = MutableLiveData<Result<LoginResponse>>()
    val loginResponse: LiveData<Result<LoginResponse>> = _loginResponse


    fun login(loginRequest: LoginRequest) = viewModelScope.launch {
        _loginResponse.value = Result.Loading()
        try {
            val response = repository.login(loginRequest)
            _loginResponse.value = Result.Success(response.body()!!)
        } catch (e: Exception){
            _loginResponse.value = Result.Error(e.message.toString())
            Log.e("LoginViewModel",e.message.toString())
        }
    }

    fun saveUser(token: String) = viewModelScope.launch {
        repository.saveUser(token)
    }

}






