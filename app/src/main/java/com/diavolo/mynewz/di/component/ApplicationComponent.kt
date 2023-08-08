package com.diavolo.mynewz.di.component

import android.content.Context
import com.diavolo.mynewz.MyNewzApplication
import com.diavolo.mynewz.data.api.NetworkService
import com.diavolo.mynewz.data.repository.NewsSourceRepository
import com.diavolo.mynewz.di.ApplicationContext
import com.diavolo.mynewz.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MyNewzApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getNewsSourceRepository(): NewsSourceRepository

}