package com.vinsol.dibear.di

import com.vinsol.dibear.di.modules.AppModule
import com.vinsol.dibear.di.modules.DatabaseModule
import com.vinsol.dibear.di.modules.NetworkModule
import com.vinsol.dibear.di.scope.AppScope
import com.vinsol.dibear.presentation.beerDetail.BeerDetailActivity
import com.vinsol.dibear.presentation.beerList.BeerListFragment
import dagger.Component

@AppScope
@Component(modules = [
    (AppModule::class),
    (NetworkModule::class),
    (DatabaseModule::class)
])
interface MainComponent {
    fun inject(beerListFragment: BeerListFragment)
    fun inject(beerDetailActivity: BeerDetailActivity)

    fun createSubGraph(): ScreenSubComponent
}