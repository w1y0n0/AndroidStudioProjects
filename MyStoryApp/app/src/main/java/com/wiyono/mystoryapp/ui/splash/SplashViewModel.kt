package com.wiyono.mystoryapp.ui.splash

import androidx.lifecycle.ViewModel
import com.wiyono.mystoryapp.data.repository.AuthRepository

class SplashViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun isLogin() = authRepository.isLogin()
}