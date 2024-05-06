package com.rongcom.newsapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rongcom.newsapp.data.api.NetworkService
import com.rongcom.newsapp.data.model.topheadlines.ApiTopHeadlines
import com.rongcom.newsapp.ui.utils.AppConstant.COUNTRY
import com.rongcom.newsapp.ui.utils.AppConstant.INITIAL_PAGE
import com.rongcom.newsapp.ui.utils.AppConstant.PAGE_SIZE
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class TopHeadlinePagingSource(private val networkService: NetworkService) : PagingSource<Int, ApiTopHeadlines>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiTopHeadlines> {
        return try {
           val page = params.key ?: INITIAL_PAGE
           val response = networkService.getTopHeadlines(
               country = COUNTRY,
               page = page,
               pageSize = PAGE_SIZE
           )
           LoadResult.Page(
               data = response.apiTopHeadlines,
               prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
               nextKey = if(response.apiTopHeadlines.isEmpty()) null else page.plus(1)
           )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ApiTopHeadlines>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}