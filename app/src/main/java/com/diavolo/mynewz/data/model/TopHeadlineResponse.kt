package com.diavolo.mynewz.data.model


import com.google.gson.annotations.SerializedName

data class TopHeadlineResponse(
    @SerializedName("sources")
    val sources: List<Source>,
    @SerializedName("status")
    val status: String
)