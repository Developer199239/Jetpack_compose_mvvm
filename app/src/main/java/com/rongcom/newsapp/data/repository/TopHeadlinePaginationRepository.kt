package com.rongcom.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rongcom.newsapp.data.api.NetworkService
import com.rongcom.newsapp.data.model.topheadlines.ApiTopHeadlines
import com.rongcom.newsapp.ui.utils.AppConstant.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopHeadlinePaginationRepository @Inject constructor(
    private val networkService: NetworkService
) {
    fun getTopHeadlinesArticles() : Flow<PagingData<ApiTopHeadlines>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TopHeadlinePagingSource(networkService)
            }
        ).flow
    }
}