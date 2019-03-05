package com.vinsol.dibear.data.sp

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

class SharedPreferencesHelper(private val localStorage: LocalStorage) {
    companion object {
        private const val IS_FIRST_TIME = "is_first_time"
    }

    fun saveIsFirstTime(isFirstTime: Boolean = false) {
        localStorage.writeBoolean(isFirstTime, IS_FIRST_TIME)
    }

    fun isFirstTime(): Boolean {
        return localStorage.readBoolean(IS_FIRST_TIME, true)
    }

    private fun serialize(obj: Any): String {
        val builder = GsonBuilder()
        val gson = builder.enableComplexMapKeySerialization().setPrettyPrinting().create()
        return gson.toJson(obj)
    }

    private fun deserialize(json: String?, type: Type): Any {
        return Gson().fromJson(json, type)
    }
}