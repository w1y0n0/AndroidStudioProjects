package id.ac.pnc.mydicodingevent.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.pnc.mydicodingevent.data.remote.response.ListEventsItem
import id.ac.pnc.mydicodingevent.repository.SearchEventsRepository
import id.ac.pnc.mydicodingevent.utils.Result

class SearchActivityViewModel(private val listEventsRepository: SearchEventsRepository) :
    ViewModel() {
    lateinit var listEvents: LiveData<Result<List<ListEventsItem>>>

    fun searchEvents(keyword: String): LiveData<Result<List<ListEventsItem>>> {
        listEvents = listEventsRepository.searchEvents(keyword)
        return listEvents
    }
}