@file:Suppress("DEPRECATION")

package com.apkupdater.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.apkupdater.R
import com.apkupdater.data.ui.Source

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcludeIcon(
    exclude: Boolean,
    @StringRes excludeString: Int,
    @StringRes includeString: Int,
    @DrawableRes excludeIcon: Int,
    @DrawableRes includeIcon: Int,
    @DrawableRes icon: Int = if (exclude) excludeIcon else includeIcon,
    @StringRes string: Int = if (exclude) includeString else excludeString,
    @StringRes contentDescription: Int = if (exclude) excludeString else includeString,
) = PlainTooltipBox(tooltip = { Text(stringResource(string)) }) {
    Icon(painterResource(icon), stringResource(contentDescription), Modifier.tooltipTrigger())
}

@Composable
fun ExcludeSystemIcon(exclude: Boolean) = ExcludeIcon(
    exclude = exclude,
    excludeString = R.string.exclude_system_apps,
    includeString = R.string.include_system_apps,
    excludeIcon = R.drawable.ic_system_off,
    includeIcon = R.drawable.ic_system
)

@Composable
fun ExcludeAppStoreIcon(exclude: Boolean) = ExcludeIcon(
    exclude = exclude,
    excludeString = R.string.exclude_app_store,
    includeString = R.string.include_app_store,
    excludeIcon = R.drawable.ic_appstore_off,
    includeIcon = R.drawable.ic_appstore
)

@Composable
fun ExcludeDisabledIcon(exclude: Boolean) = ExcludeIcon(
    exclude = exclude,
    excludeString = R.string.exclude_disabled_apps,
    includeString = R.string.include_disabled_apps,
    excludeIcon = R.drawable.ic_disabled_off,
    includeIcon = R.drawable.ic_disabled
)

@Composable
fun SourceIcon(source: Source, modifier: Modifier = Modifier) = Icon(
    painterResource(id = source.resourceId),
    source.name,
    modifier
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TooltipIcon(text: String, resId: Int, modifier: Modifier = Modifier) = PlainTooltipBox(tooltip = { Text(text) }) {
    Icon(
        painter = painterResource(id = resId),
        contentDescription = text,
        modifier = Modifier.tooltipTrigger().then(modifier)
    )
}

@Composable
fun PlayStoreIcon(modifier: Modifier = Modifier) =
        TooltipIcon(stringResource(R.string.open_in_store), R.drawable.gplay,
                    modifier.padding(Dp(8f)))

@Composable
fun RefreshIcon(modifier: Modifier = Modifier) =
        TooltipIcon(stringResource(R.string.refresh_updates), R.drawable.ic_refresh, modifier)