package com.vinsol.dibear.data.sp

interface LocalStorage {
    fun writeString(message: String, key: String)
    fun writeInt(message: Int, key: String)
    fun writeFloat(message: Float, key: String)
    fun writeLong(message: Long, key: String)
    fun writeBoolean(message: Boolean, key: String)

    fun readString(key: String): String
    fun readInt(key: String): Int
    fun readFloat(key: String): Float
    fun readLong(key: String): Long
    fun readBoolean(key: String, default: Boolean = false): Boolean
}