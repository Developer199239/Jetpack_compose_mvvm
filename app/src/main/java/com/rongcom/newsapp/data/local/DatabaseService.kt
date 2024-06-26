package com.rongcom.newsapp.data.local


import com.rongcom.newsapp.data.local.entity.NewsSources
import com.rongcom.newsapp.data.local.entity.TopHeadlineEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseService {

    fun getAllTopHeadlinesArticles(countryID: String): Flow<List<TopHeadlineEntity>>

    fun deleteAndInsertAllTopHeadlinesArticles(topHeadlineEntities: List<TopHeadlineEntity>, countryID: String)

    fun getNewsSources(): Flow<List<NewsSources>>

    fun deleteAndInsertAllNewsSources(articles: List<NewsSources>)

    fun getSourceNewsByDB(sourceID: String): Flow<List<TopHeadlineEntity>>

    fun deleteAllAndInsertAllSourceNews(articles: List<TopHeadlineEntity>, sourceID: String)

    fun getLanguageNews(languageID: String): Flow<List<TopHeadlineEntity>>

    fun deleteAllAndInsertAllLanguageArticles(articles: List<TopHeadlineEntity>, languageID: String)

}