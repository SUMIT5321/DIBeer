package com.vinsol.dibear.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinsol.dibear.data.entities.BeerData

@Database(entities = arrayOf(BeerData::class), version = 1)
@TypeConverters(Converters::class)
abstract class BeerDatabase: RoomDatabase() {
    abstract fun getBeerDAO(): BeerDAO
}