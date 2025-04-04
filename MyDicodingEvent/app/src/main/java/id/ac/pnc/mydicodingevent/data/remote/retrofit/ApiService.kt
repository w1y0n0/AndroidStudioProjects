package id.ac.pnc.mydicodingevent.data.remote.retrofit

import id.ac.pnc.mydicodingevent.data.remote.response.DetailEventResponse
import id.ac.pnc.mydicodingevent.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events?active=1")
    suspend fun getUpcomingEvents(@Query("limit") limit: Int = 40): EventResponse

    @GET("events?active=0")
    suspend fun getFinishedEvents(@Query("limit") limit: Int = 40): EventResponse

    @GET("events?active=-1")
    suspend fun searchEvents(@Query("q") q: String): EventResponse

    @GET("events/{id}")
    suspend fun getEventDetails(@Path("id") id: Int): DetailEventResponse

    @GET("events?active=1&limit=1")
    suspend fun getOneUpcomingEvent(): EventResponse
}