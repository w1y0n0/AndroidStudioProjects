package com.wiyono.mystoryapp.ui.detail

import androidx.lifecycle.*
import com.wiyono.mystoryapp.data.repository.StoryRepository

class DetailViewModel(private val storyRepository: StoryRepository): ViewModel() {

    private val storyId = MutableLiveData<String>()

    fun setStoryId(id: String) {
        storyId.value = id
    }

    val detailStory = storyId.switchMap {
        storyRepository.getDetailStory(it)
    }
}