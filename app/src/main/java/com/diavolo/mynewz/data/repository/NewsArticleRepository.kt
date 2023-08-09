package com.diavolo.mynewz.data.repository

import com.diavolo.mynewz.data.api.NetworkService
import com.diavolo.mynewz.data.model.Article
import com.diavolo.mynewz.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */
@Singleton
class NewsArticleRepository @Inject constructor(private val networkService: NetworkService) {

    fun getArticleList(source:String): Flow<List<Article>> {
        return flow {
            emit(networkService.getArticleList(source))
        }.map {
            it.articles
        }
    }

    fun searchNewsSource(query:String): Flow<List<Article>> {
        return flow {
            emit(networkService.searchNewsSource(query))
        }.map {
            it.articles
        }
    }
}