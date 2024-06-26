package com.rongcom.newsapp.data.repository

import com.rongcom.newsapp.data.api.NetworkService
import com.rongcom.newsapp.data.local.DatabaseService
import com.rongcom.newsapp.data.local.entity.TopHeadlineEntity
import com.rongcom.newsapp.data.model.topheadlines.ApiTopHeadlines
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class OfflineTopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
){
    fun getTopHeadlines(countryID: String): Flow<List<ApiTopHeadlines>> {
        return flow { emit(networkService.getTopHeadlines(countryID)) }
            .map {
                it.apiTopHeadlines
            }
    }

    fun deleteAndInsertAllTopHeadlinesArticles(topHeadlineEntities: List<TopHeadlineEntity>, country: String) {
        databaseService.deleteAndInsertAllTopHeadlinesArticles(topHeadlineEntities, country)
    }

    fun getTopHeadlinesFromDB(countryID: String): Flow<List<TopHeadlineEntity>> {
        return databaseService.getAllTopHeadlinesArticles(countryID)
    }
}