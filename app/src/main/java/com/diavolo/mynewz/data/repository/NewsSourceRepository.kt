package com.diavolo.mynewz.data.repository

import com.diavolo.mynewz.data.api.NetworkService
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
class NewsSourceRepository @Inject constructor(private val networkService: NetworkService) {

    fun getSources(category:String): Flow<List<Source>> {
        return flow {
            emit(networkService.getSources(category))
        }.map {
            it.sources
        }
    }
}