package com.diavolo.mynewz.data.api

import com.diavolo.mynewz.data.model.TopHeadlineArticleResponse
import com.diavolo.mynewz.data.model.TopHeadlineSourceResponse
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
    suspend fun getSourceList(@Query("category") category: String): TopHeadlineSourceResponse
    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getArticleList(@Query("sources") source: String): TopHeadlineArticleResponse
    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun searchNewsSource(@Query("q") query: String): TopHeadlineArticleResponse
}