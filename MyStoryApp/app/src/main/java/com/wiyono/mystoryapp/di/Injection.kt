package com.wiyono.mystoryapp.di

import android.content.Context
import com.wiyono.mystoryapp.data.local.datastore.UserPreference
import com.wiyono.mystoryapp.data.remote.RemoteDataSource
import com.wiyono.mystoryapp.data.remote.retrofit.ApiConfig
import com.wiyono.mystoryapp.data.repository.AuthRepository
import com.wiyono.mystoryapp.data.repository.StoryRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService()
        val remoteDataSource = RemoteDataSource(apiService)
        val userPreference = UserPreference.getInstance(context)
        return AuthRepository(remoteDataSource, userPreference)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val remoteDataSource = RemoteDataSource(apiService)
        val userPreference = UserPreference.getInstance(context)
        return StoryRepository(remoteDataSource, userPreference)
    }
}