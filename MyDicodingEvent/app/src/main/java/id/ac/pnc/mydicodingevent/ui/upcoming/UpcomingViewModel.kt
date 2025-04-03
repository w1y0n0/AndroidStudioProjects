package id.ac.pnc.mydicodingevent.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.pnc.mydicodingevent.data.remote.response.ListEventsItem
import id.ac.pnc.mydicodingevent.repository.UpcomingEventsRepository
import id.ac.pnc.mydicodingevent.utils.Result

class UpcomingViewModel(private val upcomingEventsRepository: UpcomingEventsRepository) : ViewModel() {

    lateinit var listEvents: LiveData<Result<List<ListEventsItem>>>

    fun getUpcomingEvent(limit: Int): LiveData<Result<List<ListEventsItem>>> {
        listEvents = upcomingEventsRepository.getUpcomingEvent(limit)
        return listEvents
    }
}