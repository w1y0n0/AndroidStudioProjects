package com.wiyono.mystoryapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wiyono.mystoryapp.di.Injection
import com.wiyono.mystoryapp.ui.auth.login.LoginViewModel
import com.wiyono.mystoryapp.ui.auth.register.RegisterViewModel
import com.wiyono.mystoryapp.ui.detail.DetailViewModel
import com.wiyono.mystoryapp.ui.main.MainViewModel
import com.wiyono.mystoryapp.ui.splash.SplashViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Injection.provideAuthRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideAuthRepository(context)) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(Injection.provideAuthRepository(context)) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(Injection.provideStoryRepository(context), Injection.provideAuthRepository(context)) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(Injection.provideStoryRepository(context)) as T
            }
//            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
//                UploadViewModel(Injection.provideStoryRepository(context)) as T
//            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}