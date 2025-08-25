package com.wiyono.mystoryapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.wiyono.mystoryapp.data.local.datastore.UserPreference
import com.wiyono.mystoryapp.data.remote.RemoteDataSource
import com.wiyono.mystoryapp.data.domain.Result
import com.wiyono.mystoryapp.data.remote.response.StoryItem
import com.wiyono.mystoryapp.data.remote.response.UploadStoryResponse
import kotlinx.coroutines.flow.first
import java.io.File

class StoryRepository(private val remoteDataSource: RemoteDataSource, private val userPreference: UserPreference) {
    fun getAllStory(): LiveData<Result<List<StoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getToken().first()
            val response = remoteDataSource.getAllStory("Bearer $token")
            val result = response.listStory
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailStory(id: String): LiveData<Result<StoryItem>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getToken().first()
            val response = remoteDataSource.detailStory("Bearer $token", id)
            val result = response.story
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun uploadStory(image: File, description: String): LiveData<Result<UploadStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getToken().first()
            val response = remoteDataSource.uploadStory("Bearer $token", image, description)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}