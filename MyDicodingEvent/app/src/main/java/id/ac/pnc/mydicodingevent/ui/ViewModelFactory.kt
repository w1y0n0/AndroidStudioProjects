package id.ac.pnc.mydicodingevent.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.ac.pnc.mydicodingevent.di.Injection
import id.ac.pnc.mydicodingevent.repository.EventRepository
import id.ac.pnc.mydicodingevent.repository.FavoriteEventRepository
import id.ac.pnc.mydicodingevent.repository.FinishedEventsRepository
import id.ac.pnc.mydicodingevent.repository.SearchEventsRepository
import id.ac.pnc.mydicodingevent.repository.UpcomingEventsRepository
import id.ac.pnc.mydicodingevent.ui.detail.DetailActivityViewModel
import id.ac.pnc.mydicodingevent.ui.favorite.FavoriteViewModel
import id.ac.pnc.mydicodingevent.ui.finished.FinishedViewModel
import id.ac.pnc.mydicodingevent.ui.search.SearchActivityViewModel
import id.ac.pnc.mydicodingevent.ui.settings.SettingsPreferences
import id.ac.pnc.mydicodingevent.ui.settings.SettingsViewModel
import id.ac.pnc.mydicodingevent.ui.settings.dataStore
import id.ac.pnc.mydicodingevent.ui.upcoming.UpcomingViewModel

class ViewModelFactory private constructor(
    private val eventRepository: EventRepository,
    private val upcomingEventsRepository: UpcomingEventsRepository,
    private val finishedEventsRepository: FinishedEventsRepository,
    private val searchEventsRepository: SearchEventsRepository,
    private val favoriteEventRepository: FavoriteEventRepository,
    private val pref: SettingsPreferences
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailActivityViewModel::class.java)) {
            return DetailActivityViewModel(eventRepository, favoriteEventRepository) as T
        } else if (modelClass.isAssignableFrom(UpcomingViewModel::class.java)) {
            return UpcomingViewModel(upcomingEventsRepository) as T
        } else if (modelClass.isAssignableFrom(FinishedViewModel::class.java)) {
            return FinishedViewModel(finishedEventsRepository) as T
        } else if (modelClass.isAssignableFrom(SearchActivityViewModel::class.java)) {
            return SearchActivityViewModel(searchEventsRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteEventRepository) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    Injection.provideUpcomingEventsRepository(context),
                    Injection.provideFinishedEventsRepository(context),
                    Injection.provideSearchEventsRepository(context),
                    Injection.provideFavoriteEventRepository(context),
                    SettingsPreferences.getInstance(context.dataStore)
                )
            }.also { instance = it }
    }
}