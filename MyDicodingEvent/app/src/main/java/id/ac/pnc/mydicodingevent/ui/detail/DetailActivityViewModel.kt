package id.ac.pnc.mydicodingevent.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.pnc.mydicodingevent.data.local.entity.FavoriteEvent
import id.ac.pnc.mydicodingevent.data.remote.response.Event
import id.ac.pnc.mydicodingevent.repository.EventRepository
import id.ac.pnc.mydicodingevent.repository.FavoriteEventRepository
import id.ac.pnc.mydicodingevent.utils.Result
import kotlinx.coroutines.launch

class DetailActivityViewModel(
    private val eventRepository: EventRepository,
    private val favoriteEventRepository: FavoriteEventRepository
) : ViewModel() {
    lateinit var event: LiveData<Result<Event>>
    private lateinit var favoriteEvent: LiveData<FavoriteEvent>

    fun getDetailEvent(id: Int): LiveData<Result<Event>> {
        event = eventRepository.getDetailEvent(id)
        return event
    }

    fun insert(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            favoriteEventRepository.insert(favoriteEvent)
        }
    }

    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvent> {
        favoriteEvent = favoriteEventRepository.getFavoriteEventById(id)
        return favoriteEvent
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            favoriteEventRepository.delete(favoriteEvent)
        }
    }
}