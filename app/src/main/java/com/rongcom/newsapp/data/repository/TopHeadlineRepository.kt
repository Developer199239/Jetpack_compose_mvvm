package com.rongcom.newsapp.data.repository

import com.rongcom.newsapp.data.api.NetworkService
import com.rongcom.newsapp.data.model.topheadlines.ApiTopHeadlines
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService){
    fun getTopHeadlines(countryId: String) : Flow<List<ApiTopHeadlines>>{
        return flow { emit(networkService.getTopHeadlines(countryId)) }
            .map {
                it.apiTopHeadlines
            }
    }
}