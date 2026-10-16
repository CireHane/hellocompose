package com.example.ch07.data

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    val gson = Gson()

    @TypeConverter
    fun listToJson(list: List<String>) = gson.toJson(list)

    @TypeConverter
    fun jsonToList(json:String) = gson.fromJson<List<String>>(
        json,
        object : TypeToken<List<String>>(){}.type
    )
}