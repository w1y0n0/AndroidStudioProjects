package id.ac.pnc.mydicodingevent.di

import android.content.Context
import id.ac.pnc.mydicodingevent.data.remote.retrofit.ApiConfig
import id.ac.pnc.mydicodingevent.repository.EventRepository
import id.ac.pnc.mydicodingevent.repository.FavoriteEventRepository
import id.ac.pnc.mydicodingevent.repository.FinishedEventsRepository
import id.ac.pnc.mydicodingevent.repository.SearchEventsRepository
import id.ac.pnc.mydicodingevent.repository.UpcomingEventsRepository

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        return EventRepository.getInstance(apiService)
    }

    fun provideUpcomingEventsRepository(context: Context): UpcomingEventsRepository {
        val apiService = ApiConfig.getApiService()
        return UpcomingEventsRepository.getInstance(apiService)
    }

    fun provideFinishedEventsRepository(context: Context): FinishedEventsRepository {
        val apiService = ApiConfig.getApiService()
        return FinishedEventsRepository.getInstance(apiService)
    }

    fun provideSearchEventsRepository(context: Context): SearchEventsRepository {
        val apiService = ApiConfig.getApiService()
        return SearchEventsRepository.getInstance(apiService)
    }

    fun provideFavoriteEventRepository(context: Context): FavoriteEventRepository {
        return FavoriteEventRepository(context)
    }
}