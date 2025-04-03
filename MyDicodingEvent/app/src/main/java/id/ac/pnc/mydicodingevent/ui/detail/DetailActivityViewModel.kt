package id.ac.pnc.mydicodingevent.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.pnc.mydicodingevent.data.remote.response.Event
import id.ac.pnc.mydicodingevent.repository.EventRepository
import id.ac.pnc.mydicodingevent.utils.Result

class DetailActivityViewModel(private val eventRepository: EventRepository) : ViewModel() {

    lateinit var event: LiveData<Result<Event>>

    fun getDetailEvent(id: Int): LiveData<Result<Event>> {
        event = eventRepository.getDetailEvent(id)
        return event
    }
}