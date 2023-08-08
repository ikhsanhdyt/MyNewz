package com.diavolo.mynewz.data.api

import com.diavolo.mynewz.data.model.TopHeadlineResponse
import com.diavolo.mynewz.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */
@Singleton
interface NetworkService {
    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines/sources")
    suspend fun getSources(@Query("category") category: String): TopHeadlineResponse
}