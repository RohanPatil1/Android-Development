package com.rohan.triviatrack.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MyListConverter {
    @TypeConverter
    fun toAnsList(value: String?): List<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromAnsList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}