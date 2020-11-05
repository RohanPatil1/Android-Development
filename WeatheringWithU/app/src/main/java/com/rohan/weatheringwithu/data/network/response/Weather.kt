package com.rohan.weatheringwithu.data.network.response


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("base")
    val base: String,

    @SerializedName("cod")
    val cod: Int,

    @SerializedName("dt")
    val dt: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<WeatherX>,

)