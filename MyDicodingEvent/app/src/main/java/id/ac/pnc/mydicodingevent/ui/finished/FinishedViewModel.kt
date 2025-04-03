package id.ac.pnc.mydicodingevent.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.pnc.mydicodingevent.data.remote.response.ListEventsItem
import id.ac.pnc.mydicodingevent.repository.FinishedEventsRepository
import id.ac.pnc.mydicodingevent.utils.Result

class FinishedViewModel(private val listEventsRepository: FinishedEventsRepository) : ViewModel() {

    lateinit var listEvents: LiveData<Result<List<ListEventsItem>>>

    fun getFinishedEvent(limit: Int): LiveData<Result<List<ListEventsItem>>> {
        listEvents = listEventsRepository.getFinishedEvent(limit)
        return listEvents
    }
}