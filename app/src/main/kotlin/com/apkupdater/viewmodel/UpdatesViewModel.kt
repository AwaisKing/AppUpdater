package com.apkupdater.viewmodel

import androidx.lifecycle.viewModelScope
import com.apkupdater.data.ui.AppUpdate
import com.apkupdater.data.ui.UpdatesUiState
import com.apkupdater.data.ui.removeId
import com.apkupdater.prefs.Prefs
import com.apkupdater.repository.UpdatesRepository
import com.apkupdater.util.launchWithMutex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex

class UpdatesViewModel(private val mainViewModel: MainViewModel, private val updatesRepository: UpdatesRepository, private val prefs: Prefs) : InstallViewModel(mainViewModel) {
    private val mutex = Mutex()
    private val state = MutableStateFlow<UpdatesUiState>(UpdatesUiState.Loading)

    init {
        subscribeToInstallLog { sendInstallSnack(state.value.updates(), it) }
    }

    fun state(): StateFlow<UpdatesUiState> = state

    fun refresh(load: Boolean = true) = viewModelScope.launchWithMutex(mutex, Dispatchers.IO) {
        if (load) state.value = UpdatesUiState.Loading
        mainViewModel.changeUpdatesBadge("")
        updatesRepository.updates().collect(::setSuccess)
    }

    fun ignoreVersion(id: Int) = viewModelScope.launchWithMutex(mutex, Dispatchers.IO) {
        val ignored = prefs.ignoredVersions.get().toMutableList()
        if (ignored.contains(id)) ignored.remove(id) else ignored.add(id)
        prefs.ignoredVersions.put(ignored)
        setSuccess(state.value.mutableUpdates())
    }

    override fun finishInstall(id: Int) = viewModelScope.launchWithMutex(mutex, Dispatchers.IO) {
        setSuccess(state.value.mutableUpdates().removeId(id))
    }

    // private fun List<AppUpdate>.filterIgnoredVersions(ignoredVersions: List<Int>) = this.filter { !ignoredVersions.contains(it.id) }

    private fun List<AppUpdate>.filterIgnoredVersions(ignored: List<Int>) = this.sortedWith { app1, app2 ->
        when {
            ignored.contains(app1?.id) && ignored.contains(app2?.id) -> 1
            ignored.contains(app2?.id)                               -> -1
            ignored.contains(app1?.id)                               -> 0
            else                                                     -> app2.name.compareTo(app1.name)
        }
    }

    private fun setSuccess(updates: List<AppUpdate>) = updates.filterIgnoredVersions(prefs.ignoredVersions.get()).run {
        state.value = UpdatesUiState.Success(this)
        mainViewModel.changeUpdatesBadge(size.toString())
    }
}