package com.rongcom.newsapp.data.model.topheadlines

import com.google.gson.annotations.SerializedName
import com.rongcom.newsapp.data.model.topheadlines.ApiTopHeadlines

data class TopHeadlinesResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("totalResults") val totalResults: Int = 0,
    @SerializedName("articles") val apiTopHeadlines: List<ApiTopHeadlines> = ArrayList(),
)