package com.rongcom.newsapp.data.repository

import com.rongcom.newsapp.data.api.NetworkService
import com.rongcom.newsapp.data.local.entity.TopHeadlineEntity
import com.rongcom.newsapp.data.model.topheadlines.toTopHeadlineEntity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class SearchRepository @Inject constructor(private val networkService: NetworkService){
    fun getNewsByQueries(query: String) : Flow<List<TopHeadlineEntity>> {
        return flow {
            emit(networkService.getNewsByQueries(query))
        }.map {
            it.apiTopHeadlines.map {data->
                data.toTopHeadlineEntity(query)
            }
        }
    }
}