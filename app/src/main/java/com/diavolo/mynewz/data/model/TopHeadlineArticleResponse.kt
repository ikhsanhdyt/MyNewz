package com.diavolo.mynewz.data.model


import com.google.gson.annotations.SerializedName

data class TopHeadlineArticleResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)