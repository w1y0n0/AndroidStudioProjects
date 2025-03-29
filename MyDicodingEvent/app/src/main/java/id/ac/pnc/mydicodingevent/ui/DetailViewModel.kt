package id.ac.pnc.mydicodingevent.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.pnc.mydicodingevent.data.response.DetailEventResponse
import id.ac.pnc.mydicodingevent.data.response.EventDetail
import id.ac.pnc.mydicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _listEventDetail = MutableLiveData<EventDetail>()
    val listEventDetail: LiveData<EventDetail> = _listEventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val client = ApiConfig.getApiService()

    fun getDetailData(id: String){
        _isLoading.value = true
        client.getDetailEvent(id).enqueue(object : Callback<DetailEventResponse>{
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _listEventDetail.value = response.body()!!.event
                } else {
                    _isLoading.value = true
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = true
            }

        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}