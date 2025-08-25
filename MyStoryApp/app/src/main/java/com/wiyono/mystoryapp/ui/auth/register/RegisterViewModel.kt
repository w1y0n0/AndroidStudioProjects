package com.wiyono.mystoryapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wiyono.mystoryapp.data.remote.response.RegisterResponse
import com.wiyono.mystoryapp.data.domain.Result
import com.wiyono.mystoryapp.data.repository.AuthRepository

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> {
        return authRepository.register(name, email, password)
    }
}