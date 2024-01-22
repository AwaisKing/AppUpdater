package com.apkupdater.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.grid.items
import com.apkupdater.R
import com.apkupdater.data.ui.AppUpdate
import com.apkupdater.ui.component.*
import com.apkupdater.ui.theme.statusBarColor
import com.apkupdater.viewmodel.UpdatesViewModel

@Composable
fun UpdatesScreen(viewModel: UpdatesViewModel) {
    viewModel.state().collectAsStateWithLifecycle().value.onLoading {
        UpdatesScreenLoading(viewModel)
    }.onError {
        DefaultErrorScreen()
    }.onSuccess {
        UpdatesScreenSuccess(viewModel, it.updates)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatesTopBar(viewModel: UpdatesViewModel) = TopAppBar(
    title = { Text(stringResource(R.string.tab_updates)) },
    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.statusBarColor()),
    actions = {
        IconButton(onClick = { viewModel.refresh() }) { RefreshIcon() }
    },
    navigationIcon = {
        Box(Modifier.minimumInteractiveComponentSize().size(40.dp), Alignment.Center) {
            Icon(Icons.Filled.ThumbUp, "Tab Icon")
        }
    }
)

@Composable
fun UpdatesScreenLoading(viewModel: UpdatesViewModel) = Column {
    UpdatesTopBar(viewModel)
    LoadingGrid()
}

@Composable
fun UpdatesScreenSuccess(viewModel: UpdatesViewModel, updates: List<AppUpdate>) = Column {
    val handler = LocalUriHandler.current

    UpdatesTopBar(viewModel)

    if (updates.isEmpty()) EmptyGrid()
    else TvGrid(viewModel, updates, handler)
}

@Composable
fun TvGrid(viewModel: UpdatesViewModel, updates: List<AppUpdate>, handler: UriHandler) = InstalledGrid {
    items(updates) { update ->
        UpdateItem(
            update,
            viewModel,
            { viewModel.openUri(update, handler) },
            { viewModel.openPlayStore(update, handler) },
            { viewModel.ignoreVersion(update.id) },
        )
    }
}