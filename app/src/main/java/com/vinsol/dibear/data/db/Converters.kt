package com.vinsol.dibear.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun stringFromList(list: List<String>): String? {
        val builder = GsonBuilder()
        val gson = builder.enableComplexMapKeySerialization().setPrettyPrinting().create()
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToList(json: String?): List<String>? {
        val type = object : TypeToken<List<String>>(){}.type
        return Gson().fromJson(json, type)
    }
}