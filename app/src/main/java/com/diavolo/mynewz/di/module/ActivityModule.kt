package com.diavolo.mynewz.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.diavolo.mynewz.data.repository.NewsCategoryRepository
import com.diavolo.mynewz.data.repository.NewsSourceRepository
import com.diavolo.mynewz.di.ActivityContext
import com.diavolo.mynewz.ui.base.ViewModelProviderFactory
import com.diavolo.mynewz.ui.newsCategory.NewsCategoryAdapter
import com.diavolo.mynewz.ui.newsCategory.NewsCategoryViewModel
import com.diavolo.mynewz.ui.newsSource.NewsSourceViewModel
import dagger.Module
import dagger.Provides


/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */
@Module
class ActivityModule(private val activity: AppCompatActivity) {
    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideNewsCategoryViewModel(repository: NewsCategoryRepository): NewsCategoryViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory.ViewModelProviderFactory(NewsCategoryViewModel::class) {
                NewsCategoryViewModel(repository)
            })[NewsCategoryViewModel::class.java]
    }

    @Provides
    fun provideNewsSourceViewModel(repository: NewsSourceRepository): NewsSourceViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory.ViewModelProviderFactory(NewsSourceViewModel::class) {
                NewsSourceViewModel(repository)
            })[NewsSourceViewModel::class.java]
    }

}