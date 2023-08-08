package com.diavolo.mynewz.di.module

import android.content.Context
import com.diavolo.mynewz.MyNewzApplication
import com.diavolo.mynewz.data.api.NetworkService
import com.diavolo.mynewz.di.ApplicationContext
import com.diavolo.mynewz.di.BaseUrl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */

@Module
class ApplicationModule(private val application: MyNewzApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }


    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }
}