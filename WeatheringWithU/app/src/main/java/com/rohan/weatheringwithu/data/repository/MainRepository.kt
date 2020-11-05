package com.rohan.weatheringwithu.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.rohan.weatheringwithu.data.network.BASE_URL
import com.rohan.weatheringwithu.data.network.WeatherInterface
import com.rohan.weatheringwithu.data.network.response.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainRepository {

    private val showLoading = MutableLiveData<Boolean>()
    private val weatherResponse = MutableLiveData<Weather>()


    fun getLoadingState(): LiveData<Boolean> {
        return showLoading
    }

    fun getWeather(): MutableLiveData<Weather> {
        return weatherResponse
    }


    fun searchWeather(queryStr: String): MutableLiveData<Weather> {
        Log.d("OPENW", " " + "MainRepository searchWeather(${queryStr})")

        showLoading.postValue(true)

        //FetchData
        val retrofitCall =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofitCall.create(WeatherInterface::class.java)
        service.getWeather(queryStr).enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                showLoading.postValue(false)
                weatherResponse.postValue(response.body())
                Log.d("OPENW", "onResponse: ${Gson().toJson(response.body())}")
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                showLoading.postValue(false)
                Log.d("OPENW", "FAILED!!!!  "+t.message)


            }
        })

        return weatherResponse
    }


}