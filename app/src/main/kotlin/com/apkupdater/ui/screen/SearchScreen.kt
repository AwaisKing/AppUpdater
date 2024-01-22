package com.apkupdater.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.grid.items
import com.apkupdater.R
import com.apkupdater.data.ui.SearchUiState
import com.apkupdater.ui.component.DefaultErrorScreen
import com.apkupdater.ui.component.LoadingGrid
import com.apkupdater.ui.component.SearchItem
import com.apkupdater.ui.component.InstalledGrid
import com.apkupdater.ui.theme.statusBarColor
import com.apkupdater.viewmodel.SearchViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel = koinViewModel()) = Column {
    SearchTopBar(viewModel)
    viewModel.state().collectAsStateWithLifecycle().value.onError {
        DefaultErrorScreen()
    }.onSuccess {
        SearchScreenSuccess(it, viewModel)
    }.onLoading {
        LoadingGrid()
    }
}

@Composable
fun SearchScreenSuccess(state: SearchUiState.Success, viewModel: SearchViewModel) = Column {
    val uriHandler = LocalUriHandler.current
    InstalledGrid {
        items(state.updates) { update -> SearchItem(update) { viewModel.openUri(update, uriHandler) } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(viewModel: SearchViewModel) = TopAppBar(
    title = { SearchText(viewModel) },
    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.statusBarColor()),
    actions = {},
    navigationIcon = {
        Box(Modifier.minimumInteractiveComponentSize().size(40.dp), Alignment.Center) {
            Icon(Icons.Filled.Search, "Tab Icon")
        }
    }
)

@Composable
fun SearchText(viewModel: SearchViewModel) = Box {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var value by remember { mutableStateOf("") }
    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier.fillMaxWidth().padding(0.dp).focusRequester(focusRequester),
        label = { Text(stringResource(R.string.tab_search)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        maxLines = 1,
        singleLine = true
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    LaunchedEffect(value) {
        if (value.length >= 3) {
            delay(1000)
            viewModel.search(value)
        }
    }
}
