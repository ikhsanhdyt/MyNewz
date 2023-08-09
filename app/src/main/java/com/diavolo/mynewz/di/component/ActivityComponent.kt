package com.diavolo.mynewz.di.component

import com.diavolo.mynewz.MyNewzApplication
import com.diavolo.mynewz.di.ActivityScope
import com.diavolo.mynewz.di.module.ActivityModule
import com.diavolo.mynewz.ui.newsArticle.NewsArticleActivity
import com.diavolo.mynewz.ui.newsCategory.NewsCategoryActivity
import com.diavolo.mynewz.ui.newsDetailWebView.NewsDetailWebViewActivity
import com.diavolo.mynewz.ui.newsSource.NewsSourceActivity
import dagger.Component

/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */
@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: NewsCategoryActivity)
    fun inject(activity: NewsSourceActivity)
    fun inject(activity: NewsArticleActivity)
    fun inject(activity: NewsDetailWebViewActivity)

}