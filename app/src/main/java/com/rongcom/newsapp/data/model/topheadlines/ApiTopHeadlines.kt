package com.rongcom.newsapp.data.model.topheadlines

import com.google.gson.annotations.SerializedName
import com.rongcom.newsapp.data.local.entity.TopHeadlineEntity

data class ApiTopHeadlines(
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String = "",
    @SerializedName("urlToImage") val imageUrl: String = "",
    @SerializedName("source") val apiSource: ApiSource
)

fun ApiTopHeadlines.toTopHeadlineEntity(country: String) = TopHeadlineEntity(
    title = title,
    description = description ?: "",
    url = url,
    imageUrl = imageUrl ?: "",
    country = country,
    source = apiSource.toSourceEntity()
)

fun ApiTopHeadlines.toArticleLanguage(language: String): TopHeadlineEntity {
    return TopHeadlineEntity(
        title = title,
        description = description ?: "",
        url = url,
        imageUrl = imageUrl ?: "",
        language = language,
        source = apiSource.toSourceEntity()
    )
}

fun List<ApiTopHeadlines>.apiTopHeadlinesListToTopHeadlineEntityList(country: String): List<TopHeadlineEntity> {
    val list = mutableListOf<TopHeadlineEntity>()
    forEach { apiArticle ->
        list.add(apiArticle.toTopHeadlineEntity(country))
    }
    return list
}