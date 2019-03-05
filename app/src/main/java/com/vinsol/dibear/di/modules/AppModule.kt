package com.vinsol.dibear.di.modules

import android.content.Context
import com.squareup.picasso.Picasso
import com.vinsol.dibear.di.scope.AppScope
import com.vinsol.dibear.presentation.common.BeerApp
import com.vinsol.dibear.presentation.common.ImageLoader
import com.vinsol.dibear.presentation.common.PicassoImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: BeerApp) {

    @Provides
    @AppScope
    fun provideAppContext(): Context = app

    @Provides
    @AppScope
    fun provideApp(): BeerApp = app

    @Provides
    @AppScope
    fun provideImageLoader(): ImageLoader = PicassoImageLoader(Picasso.with(app))
}