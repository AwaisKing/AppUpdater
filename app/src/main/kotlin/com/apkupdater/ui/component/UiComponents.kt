package com.apkupdater.ui.component

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.apkupdater.R
import com.apkupdater.data.ui.*
import com.apkupdater.util.getAppName
import com.apkupdater.util.toAnnotatedString
import com.apkupdater.viewmodel.InstallViewModel

@Preview
@Composable
fun DefaultErrorScreen() = Box(Modifier.fillMaxSize()) {
    HugeText(stringResource(R.string.something_went_wrong), Modifier.align(Alignment.Center), 2)
}

@Composable
fun CommonItem(
    packageName: String,
    name: String,
    version: String,
    oldVersion: String?,
    versionCode: Long,
    oldVersionCode: Long?,
    uri: Uri? = null,
    single: Boolean = false,
) = Row {
    if (uri == null) {
        LoadingImageApp(packageName, Modifier.height(100.dp).align(Alignment.CenterVertically).padding(top = 8.dp))
    } else {
        LoadingImage(uri, Modifier.height(100.dp).align(Alignment.CenterVertically).padding(top = 8.dp))
    }
    Column(Modifier.align(Alignment.CenterVertically).padding(start = 8.dp, end = 8.dp, top = 8.dp)) {
        MediumTitle(name.ifEmpty { LocalContext.current.getAppName(packageName) })
        Text(packageName)

        if (oldVersion != null && !single) ScrollableText { MediumText("$oldVersion → $version") }
        else MediumText(version)

        val code = if (versionCode == 0L) "?" else versionCode.toString()
        MediumText(if (oldVersionCode != null && !single) "$oldVersionCode → $code" else code)
    }
}

@Composable
fun BoxScope.SourceIcon(app: AppUpdate) = SourceIcon(
    app.source,
    Modifier.align(Alignment.CenterStart).padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
        .size(32.dp)
)

@Composable
fun IgnoreVersionButton(
    app: AppUpdate,
    viewModel: InstallViewModel,
    onIgnoreVersion: (Int) -> Unit,
) = ElevatedButton(
    modifier = Modifier.padding(top = 0.dp, bottom = 8.dp, start = 0.dp, end = 0.dp),
    onClick = { onIgnoreVersion(app.id) }
) {
    Text(stringResource(if (viewModel.isVersionIgnored(app)) R.string.unignore_version
                        else R.string.ignore_version))
}

@Composable
fun InstallButton(app: AppUpdate, onInstall: (String) -> Unit) = ElevatedButton(
    modifier = Modifier.padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp).width(120.dp),
    onClick = { onInstall(app.packageName) }
) { Text(stringResource(R.string.install_cd)) }

@Composable
fun OpenInPlayStoreButton(app: AppUpdate, onPlayStoreOpen: (String) -> Unit) = OutlinedIconButton(
    modifier = Modifier.padding(top = 0.dp, bottom = 8.dp, start = 0.dp, end = 8.dp),
    onClick = { onPlayStoreOpen(app.packageName) }
) { PlayStoreIcon() }

@Composable
fun WhatsNew(whatsNew: String, source: Source) {
    if (whatsNew.isEmpty()) return
    val text = if (source != ApkMirrorSource && source != ApkPureSource) AnnotatedString(whatsNew)
    else HtmlCompat.fromHtml(whatsNew.trim(), HtmlCompat.FROM_HTML_MODE_COMPACT).toAnnotatedString()
    ExpandingAnnotatedText(text, Modifier.padding(8.dp).fillMaxWidth())
}

@Composable
fun InstalledItem(app: AppInstalled, onIgnore: (String) -> Unit = {}) = Card(
    modifier = Modifier.alpha(if (app.ignored) 0.5f else 1f)
) {
    Column {
        CommonItem(app.packageName, app.name, app.version, null, app.versionCode, null)
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End) {
            ElevatedButton(
                modifier = Modifier.padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                onClick = { onIgnore(app.packageName) }
            ) {
                Text(stringResource(if (app.ignored) R.string.unignore_cd else R.string.ignore_cd))
            }
        }
    }
}

@Composable
fun UpdateItem(
    app: AppUpdate,
    viewModel: InstallViewModel,
    onInstall: (String) -> Unit = {},
    onPlayStoreOpen: (String) -> Unit = {},
    onIgnoreVersion: (Int) -> Unit,
) = Card(
    modifier = Modifier.alpha(if (viewModel.isVersionIgnored(app)) 0.5f else 1f)
) {
    Column {
        CommonItem(app.packageName, app.name, app.version, app.oldVersion, app.versionCode, app.oldVersionCode)
        WhatsNew(app.whatsNew, app.source)
        Box {
            SourceIcon(app)
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End) {
                IgnoreVersionButton(app, viewModel, onIgnoreVersion)
                InstallButton(app, onInstall)
                OpenInPlayStoreButton(app, onPlayStoreOpen)
            }
        }
    }
}

@Composable
fun SearchItem(app: AppUpdate, onInstall: (String) -> Unit = {}) = Card {
    Column {
        CommonItem(app.packageName, app.name, app.version, app.oldVersion, app.versionCode, app.oldVersionCode, app.iconUri, true)
        WhatsNew(app.whatsNew, app.source)
        Box {
            SourceIcon(app)
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End) {
                InstallButton(app, onInstall)
            }
        }
    }
}
