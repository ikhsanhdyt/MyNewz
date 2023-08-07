package com.diavolo.mynewz

import android.app.Application
import com.diavolo.mynewz.di.component.ApplicationComponent
import com.diavolo.mynewz.di.component.DaggerApplicationComponent
import com.diavolo.mynewz.di.module.ApplicationModule

class MyNewzApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}