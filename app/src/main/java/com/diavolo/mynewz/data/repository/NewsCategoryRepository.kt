package com.diavolo.mynewz.data.repository

import com.diavolo.mynewz.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Written with passion by Ikhsan Hidayat on 07/08/2023.
 */
class NewsCategoryRepository @Inject constructor(){
    fun fetchNewsCategory(): Flow<List<String>> {
        return flow {
            emit(AppConstant.CATEGORY_LIST)
        }
    }
}