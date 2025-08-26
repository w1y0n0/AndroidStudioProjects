package com.wiyono.mystoryapp.ui.add.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wiyono.mystoryapp.data.repository.StoryRepository
import com.wiyono.mystoryapp.data.domain.Result
import com.wiyono.mystoryapp.data.remote.response.UploadStoryResponse
import java.io.File

class UploadViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun storyUpload(image: File, description: String): LiveData<Result<UploadStoryResponse>> {
        return storyRepository.uploadStory(image, description)
    }
}