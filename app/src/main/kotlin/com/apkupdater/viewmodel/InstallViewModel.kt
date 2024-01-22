package com.apkupdater.viewmodel

import android.util.Log
import androidx.compose.ui.platform.UriHandler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apkupdater.data.snack.InstallSnack
import com.apkupdater.data.ui.AppInstallStatus
import com.apkupdater.data.ui.AppUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class InstallViewModel(private val mainViewModel: MainViewModel) : ViewModel() {
    protected abstract fun finishInstall(id: Int): Job

    fun isVersionIgnored(app: AppUpdate) = mainViewModel.isVersionIgnored(app)

    fun openUri(update: AppUpdate, uriHandler: UriHandler) {
        Log.d("AWAISKING_APP", "install: ${update.id} -- ${update.name} -- ${update.source} -- ${update.link} ")
        uriHandler.openUri(update.link)
    }

    fun openPlayStore(update: AppUpdate, uriHandler: UriHandler) = try {
        uriHandler.openUri("market://details?id=" + update.packageName)
    } catch (e: Exception) {
        Log.e("AWAISKING_APP", "", e)
        uriHandler.openUri("https://play.google.com/store/apps/details?id=" + update.packageName)
    }

    protected fun subscribeToInstallLog(block: (AppInstallStatus) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        mainViewModel.appInstallLog.collect {
            block(it)
            if (it.success) finishInstall(it.id).join()
        }
    }

    protected fun sendInstallSnack(updates: List<AppUpdate>, log: AppInstallStatus) {
        if (log.snack) {
            updates.find { log.id == it.id }?.let { app ->
                mainViewModel.sendSnack(InstallSnack(log.success, app.name))
            }
        }
    }
}