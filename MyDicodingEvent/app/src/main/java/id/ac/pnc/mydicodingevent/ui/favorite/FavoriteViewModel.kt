package id.ac.pnc.mydicodingevent.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.pnc.mydicodingevent.data.local.entity.FavoriteEvent
import id.ac.pnc.mydicodingevent.repository.FavoriteEventRepository


class FavoriteViewModel(private val favoriteEventRepository: FavoriteEventRepository) :
    ViewModel() {
    lateinit var favoriteEvent: LiveData<List<FavoriteEvent>>

    fun getFavoriteEvents(): LiveData<List<FavoriteEvent>> {
        favoriteEvent = favoriteEventRepository.getFavoriteEvents()
        return favoriteEvent
    }

}