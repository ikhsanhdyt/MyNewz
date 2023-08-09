package com.diavolo.mynewz.di.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.diavolo.mynewz.MyNewzApplication
import com.diavolo.mynewz.data.api.NetworkService
import com.diavolo.mynewz.di.ActivityContext
import com.diavolo.mynewz.di.ApplicationContext
import com.diavolo.mynewz.di.BaseUrl
import com.diavolo.mynewz.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        chuckerInterceptor: ChuckerInterceptor
    ): NetworkService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(
                okHttpClient
            )
            .build()
            .create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context): NetworkUtils = NetworkUtils(context)
}