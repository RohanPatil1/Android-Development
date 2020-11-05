package com.rohan.weatheringwithu.data.network

import com.rohan.weatheringwithu.data.network.response.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val API_KEY = "546334b60167af59b81cd38378b99450"

interface WeatherInterface {

    //https://api.openweathermap.org/data/2.5/weather?q=Mumbai&units=metric&APPID=546334b60167af59b81cd38378b99450

    @GET("weather?")
    fun getWeather(
        @Query("q") searchStr: String,
        @Query("units") units: String = "metric",
        @Query("APPID") APPID: String = API_KEY,
        ): Call<Weather>

}