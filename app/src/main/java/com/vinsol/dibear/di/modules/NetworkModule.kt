package com.vinsol.dibear.di.modules


import com.vinsol.dibear.data.api.BeerApi
import com.vinsol.dibear.data.api.RemoteRepoImpl
import com.vinsol.dibear.data.api.beerBaseUrl
import com.vinsol.dibear.data.repository.RemoteRepo
import com.vinsol.dibear.di.scope.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @AppScope
    fun provideRetrofit(): Retrofit {

        val interceptor = (HttpLoggingInterceptor())
            .apply { level = HttpLoggingInterceptor.Level.BODY }

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(beerBaseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun provideBeerApi(retrofit: Retrofit): BeerApi {
        return retrofit.create(BeerApi::class.java)
    }

    @Provides
    @AppScope
    fun provideRemoteBeerRepo(api: BeerApi): RemoteRepo {
        return RemoteRepoImpl(api)
    }
}