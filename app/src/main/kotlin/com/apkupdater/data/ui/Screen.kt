package com.apkupdater.data.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.apkupdater.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
    val iconSelected: ImageVector,
) {
    data object Apps : Screen("apps", R.string.tab_apps, Icons.Outlined.Home, Icons.Filled.Home)
    data object Search : Screen("search", R.string.tab_search, Icons.Outlined.Search, Icons.Filled.Search)
    data object Updates : Screen("updates", R.string.tab_updates, Icons.Outlined.Build, Icons.Filled.Build)
    data object Settings : Screen("settings", R.string.tab_settings, Icons.Outlined.Settings, Icons.Filled.Settings)
}