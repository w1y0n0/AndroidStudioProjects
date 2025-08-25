package com.wiyono.mystoryapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wiyono.mystoryapp.data.domain.Result
import com.wiyono.mystoryapp.data.remote.response.LoginResponse
import com.wiyono.mystoryapp.data.repository.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun login(email: String, password: String): LiveData<Result<LoginResponse>> {
        return authRepository.login(email, password)
    }
}