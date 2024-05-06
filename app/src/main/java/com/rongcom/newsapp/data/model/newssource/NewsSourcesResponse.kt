package com.rongcom.newsapp.data.model.newssource

import com.google.gson.annotations.SerializedName
import com.praveenpayasi.headlinehub.data.model.newssource.APINewsSource

data class NewsSourcesResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("sources") val newsSource: List<APINewsSource> = arrayListOf(),
)