package id.ac.pnc.mydicodingevent.repository

import android.content.Context
import id.ac.pnc.mydicodingevent.data.local.entity.FavoriteEvent
import id.ac.pnc.mydicodingevent.data.local.entity.FavoriteEventDao
import id.ac.pnc.mydicodingevent.data.local.room.FavoriteEventRoomDatabase

class FavoriteEventRepository(context: Context) {
    private val mFavoriteEventDao: FavoriteEventDao

    init {
        val db = FavoriteEventRoomDatabase.getDatabase(context)
        mFavoriteEventDao = db.favoriteEventDao()
    }

    suspend fun insert(favoriteEvent: FavoriteEvent) {
        mFavoriteEventDao.insert(favoriteEvent)
    }

    fun getFavoriteEventById(id: Int) = mFavoriteEventDao.getFavoriteEventById(id)

    suspend fun delete(favoriteEvent: FavoriteEvent) {
        mFavoriteEventDao.delete(favoriteEvent)
    }

    fun getFavoriteEvents() = mFavoriteEventDao.getFavoriteEvents()
}