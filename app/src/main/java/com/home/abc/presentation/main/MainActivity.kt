package com.home.abc.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.abc.di.AppDependencies
import com.home.abc.ui.theme.ABCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val factory = remember {
                MainViewModelFactory(
                    pagesRepository = AppDependencies.pagesRepository,
                    filterItemsUseCase = AppDependencies.filterItemsUseCase,
                    topCharactersUseCase = AppDependencies.topCharactersUseCase
                )
            }
            val viewModel: MainViewModel = viewModel(factory = factory)
            val state by viewModel.state.collectAsStateWithLifecycle()
            ABCTheme {
                MainScreen(
                    state = state,
                    onIntent = viewModel::onIntent
                )
            }
        }
    }
}