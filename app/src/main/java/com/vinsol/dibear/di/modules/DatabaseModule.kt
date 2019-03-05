package com.vinsol.dibear.di.modules

import android.content.Context
import androidx.room.Room
import com.vinsol.dibear.data.api.BeerApi
import com.vinsol.dibear.data.api.RemoteRepoImpl
import com.vinsol.dibear.data.db.BeerDatabase
import com.vinsol.dibear.data.db.LocalRepoImpl
import com.vinsol.dibear.data.repository.LocalRepo
import com.vinsol.dibear.data.repository.RemoteRepo
import com.vinsol.dibear.di.scope.AppScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @AppScope
    @Provides
    fun provideRoomDatabase(context: Context): BeerDatabase {
        return Room.databaseBuilder(
            context, BeerDatabase::class.java, "beers").build()
    }

    @AppScope
    @Provides
    fun provideLocalBeerRepo(database: BeerDatabase): LocalRepo {
        return LocalRepoImpl(database = database)
    }
}