package com.debanshu777.compose_github.ui.base

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.debanshu777.compose_github.network.dataSource.GitHubViewModel
import com.debanshu777.compose_github.ui.base.components.BottomBar
import com.debanshu777.compose_github.ui.base.components.MainAppBar
import com.debanshu777.compose_github.ui.feature_search.state.SearchState
import com.debanshu777.compose_github.ui.feature_search.state.SearchWidgetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: GitHubViewModel) {
    val navController = rememberNavController()
    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState
    Scaffold(
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(it)
                },
                onCloseClicked = {
                    viewModel.updateSearchTextState("")
                    viewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                    viewModel.searchState.value = SearchState(data = emptyList())
                    navController.navigate(Screen.TrendingScreen.route)
                },
                onSearchClick = {
                    viewModel.searchUser(it)
                },
                onSearchTriggered = {
                    navController.navigate(Screen.SearchScreen.route)
                    viewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
                }
            )
        },
        bottomBar = { BottomBar(navController = navController) },
        content = Navigation(viewModel, navController)
    )
}
