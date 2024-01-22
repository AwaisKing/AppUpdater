package com.apkupdater.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.grid.items
import com.apkupdater.R
import com.apkupdater.data.ui.AppsUiState
import com.apkupdater.ui.component.*
import com.apkupdater.ui.theme.statusBarColor
import com.apkupdater.viewmodel.AppsViewModel
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun AppsScreen(viewModel: AppsViewModel = koinViewModel()) {
    viewModel.state().collectAsStateWithLifecycle().value.onLoading {
        AppsScreenLoading(viewModel, it)
    }.onError {
        DefaultErrorScreen()
    }.onSuccess {
        AppsScreenSuccess(viewModel, it)
    }
}

@Composable
fun AppsScreenSuccess(viewModel: AppsViewModel, state: AppsUiState.Success) = Column {
    AppsTopBar(viewModel, state.excludeSystem, state.excludeAppStore, state.excludeDisabled)
    InstalledGrid {
        items(state.apps) {
            InstalledItem(it) { app -> viewModel.ignore(app) }
        }
    }
}

@Composable
fun AppsScreenLoading(viewModel: AppsViewModel, state: AppsUiState.Loading) = Column {
    AppsTopBar(viewModel, state.excludeSystem, state.excludeAppStore, state.excludeDisabled)
    LoadingGrid()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppsTopBar(
    viewModel: AppsViewModel,
    excludeSystem: Boolean,
    excludeAppStore: Boolean,
    excludeDisabled: Boolean,
) = TopAppBar(
    title = { Text(stringResource(R.string.tab_apps)) },
    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.statusBarColor()),
    actions = {
        IconButton(onClick = { viewModel.onSystemClick() }) {
            ExcludeSystemIcon(excludeSystem)
        }
        IconButton(onClick = { viewModel.onAppStoreClick() }) {
            ExcludeAppStoreIcon(excludeAppStore)
        }
        IconButton(onClick = { viewModel.onDisabledClick() }) {
            ExcludeDisabledIcon(excludeDisabled)
        }
    },
    navigationIcon = {
        Box(Modifier.minimumInteractiveComponentSize().size(40.dp), Alignment.Center) {
            Icon(Icons.Filled.Home, "Tab Icon")
        }
    }
)