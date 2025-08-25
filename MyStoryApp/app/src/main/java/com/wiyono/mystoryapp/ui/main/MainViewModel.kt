package com.wiyono.mystoryapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.wiyono.mystoryapp.data.repository.AuthRepository
import com.wiyono.mystoryapp.data.repository.StoryRepository
import kotlinx.coroutines.launch

class MainViewModel(private val storyRepository: StoryRepository, private val authRepository: AuthRepository): ViewModel() {

    private val refresh = MutableLiveData<Unit>()
    init {
        refreshData()
    }

    fun getAllStory() = refresh.switchMap {
        storyRepository.getAllStory()
    }

    fun refreshData() {
        refresh.value = Unit
    }

    fun logout() = viewModelScope.launch {
        authRepository.logout()
    }
}