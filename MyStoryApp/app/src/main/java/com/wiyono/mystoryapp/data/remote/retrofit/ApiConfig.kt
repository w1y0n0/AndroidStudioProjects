package com.wiyono.mystoryapp.data.remote.retrofit

import retrofit2.Retrofit

class ApiConfig {
    fun getApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}