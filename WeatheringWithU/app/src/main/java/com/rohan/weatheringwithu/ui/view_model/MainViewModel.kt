package com.rohan.weatheringwithu.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rohan.weatheringwithu.data.network.response.Weather
import com.rohan.weatheringwithu.data.repository.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val showLoading: LiveData<Boolean> by lazy {
        repository.getLoadingState()
    }

    val weatherResponse: LiveData<Weather> by lazy {
        repository.getWeather()
    }


    fun searchWeather(queryStr: String) {
        Log.d("OPENW", " " + "MainViewModel searchWeather(${queryStr})")

        repository.searchWeather(queryStr)
    }

}