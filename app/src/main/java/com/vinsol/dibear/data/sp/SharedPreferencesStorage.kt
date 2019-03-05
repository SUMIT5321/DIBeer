package com.vinsol.dibear.data.sp

import android.content.Context

class SharedPreferencesStorage(val context: Context): LocalStorage {

    companion object {
        private const val DEFAULT_PREFERENCES = "DIBear"
    }

    private val sharedPreferences get() = context.getSharedPreferences(DEFAULT_PREFERENCES, Context.MODE_PRIVATE)

    override fun writeString(message: String, key: String) {
        sharedPreferences.edit().putString(key, message).apply()
    }

    override fun writeInt(message: Int, key: String) {
        sharedPreferences.edit().putInt(key, message).apply()
    }

    override fun writeFloat(message: Float, key: String) {
        sharedPreferences.edit().putFloat(key, message).apply()
    }

    override fun writeLong(message: Long, key: String) {
        sharedPreferences.edit().putLong(key, message).apply()
    }

    override fun writeBoolean(message: Boolean, key: String) {
        sharedPreferences.edit().putBoolean(key, message).apply()
    }

    override fun readString(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun readInt(key: String): Int {
        return sharedPreferences.getInt(key, Integer.MIN_VALUE)
    }

    override fun readFloat(key: String): Float {
        return sharedPreferences.getFloat(key, -1f)
    }

    override fun readLong(key: String): Long {
        return sharedPreferences.getLong(key, Long.MIN_VALUE)
    }

    override fun readBoolean(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }
}