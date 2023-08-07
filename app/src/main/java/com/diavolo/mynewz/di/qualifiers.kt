package com.diavolo.mynewz.di

import javax.inject.Qualifier

/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */
@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ActivityContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class BaseUrl