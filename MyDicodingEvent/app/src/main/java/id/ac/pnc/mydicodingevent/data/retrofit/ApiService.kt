package id.ac.pnc.mydicodingevent.data.retrofit

import id.ac.pnc.mydicodingevent.data.response.DetailEventResponse
import id.ac.pnc.mydicodingevent.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    fun getEvent(@Query("active") active: String): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): Call<DetailEventResponse>
}