package com.vinsol.dibear.presentation.common

import android.app.Application
import com.vinsol.dibear.di.DaggerMainComponent
import com.vinsol.dibear.di.MainComponent
import com.vinsol.dibear.di.modules.AppModule
import timber.log.Timber

class BeerApp: Application() {

    lateinit var mainComponent: MainComponent

    override fun onCreate() {
        super.onCreate()

        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(this))
            .build()

        Timber.plant(Timber.DebugTree())
    }
}