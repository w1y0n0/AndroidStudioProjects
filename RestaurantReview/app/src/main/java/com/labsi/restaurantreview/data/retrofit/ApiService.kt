package com.labsi.restaurantreview.data.retrofit

import com.labsi.restaurantreview.data.response.RestaurantResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("detail/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Call<RestaurantResponse>
}