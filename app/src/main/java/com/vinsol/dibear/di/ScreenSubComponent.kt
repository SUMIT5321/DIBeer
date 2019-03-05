package com.vinsol.dibear.di

import com.vinsol.dibear.di.modules.SPModule
import com.vinsol.dibear.di.scope.ScreenScope
import com.vinsol.dibear.presentation.MainActivity
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [SPModule::class])
interface ScreenSubComponent {
    fun inject(mainActivity: MainActivity)
}