package com.vinsol.dibear.di.modules

import android.content.Context
import com.vinsol.dibear.data.sp.LocalStorage
import com.vinsol.dibear.data.sp.SharedPreferencesHelper
import com.vinsol.dibear.data.sp.SharedPreferencesStorage
import com.vinsol.dibear.di.scope.ScreenScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SPModule {

    @ScreenScope
    @Provides
    fun provideSharedPreferencesHelper(context: Context): SharedPreferencesHelper {
        return SharedPreferencesHelper(SharedPreferencesStorage(context))
    }
}