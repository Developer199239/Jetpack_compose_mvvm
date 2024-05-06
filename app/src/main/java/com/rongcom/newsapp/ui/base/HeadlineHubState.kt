package com.rongcom.newsapp.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rongcom.newsapp.R


@Composable
fun rememberHeadlineHubAppState(navController: NavController) : HeadlineHubState {
    return remember(navController) {
        HeadlineHubState(navController)
    }
}

class HeadlineHubState(private val navController: NavController) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowBackIcon: Boolean
        @Composable get() = when(currentDestination?.route) {
            Route.HomeScreen.name -> false
            else -> true
        }

    val showScreenTitle: String
        @Composable get() = when(navController.currentDestination?.route) {
            Route.HomeScreen.name -> stringResource(id = R.string.app_name)
            Route.TopHeadline.name -> stringResource(id = R.string.top_headlines)
            Route.PaginationTopHeadline.name -> stringResource(id = R.string.pagination_top_headlines)
            Route.OfflineTopHeadline.name -> stringResource(id = R.string.offline_top_headlines)
            Route.NewsSources.name -> stringResource(id = R.string.news_sources)
            Route.CountryList.name -> stringResource(id = R.string.countries)
            Route.LanguageList.name -> stringResource(id = R.string.language)
            Route.Search.name -> stringResource(id = R.string.search)
            else -> ""

        }
}