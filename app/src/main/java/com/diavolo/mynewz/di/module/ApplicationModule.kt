package com.diavolo.mynewz.di.module

import android.content.Context
import com.diavolo.mynewz.MyNewzApplication
import com.diavolo.mynewz.di.ApplicationContext
import dagger.Module
import dagger.Provides

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
}