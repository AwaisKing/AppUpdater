package com.apkupdater.prefs

import com.apkupdater.data.ui.Screen
import com.kryptoprefs.context.KryptoContext
import com.kryptoprefs.gson.json
import com.kryptoprefs.preferences.KryptoPrefs


class Prefs(prefs: KryptoPrefs): KryptoContext(prefs) {
	val ignoredApps = json("ignoredApps", emptyList<String>(), true)
	val ignoredVersions = json("ignoredVersions", emptyList<Int>(), true)
	val excludeSystem = boolean("excludeSystem", defValue = true, backed = true)
	val excludeDisabled = boolean("excludeDisabled", defValue = true, backed = true)
	val excludeStore = boolean("excludeStore", defValue = false, backed = true)
	val ignoreAlpha = boolean("ignoreAlpha", defValue = true, backed = true)
	val ignoreBeta = boolean("ignoreBeta", defValue = true, backed = true)
	val ignorePreRelease = boolean("ignorePreRelease", defValue = true, backed = true)
	val useSafeStores = boolean("useSafeStores", defValue = true, backed = true)
	val useApkMirror = boolean("useApkMirror", defValue = true, backed = true)
	val useGitHub = boolean("useGitHub", defValue = true, backed = true)
	val useGitLab = boolean("useGitLab", defValue = true, backed = true)
	val useFdroid = boolean("useFdroid", defValue = true, backed = true)
	val useIzzy = boolean("useIzzy", defValue = true, backed = true)
	val useAptoide = boolean("useAptoide", defValue = true, backed = true)
	val useApkPure = boolean("useApkPure", defValue = true, backed = true)
	val enableAlarm = boolean("enableAlarm", defValue = false, backed = true)
	val alarmHour = int("alarmHour", defValue = 12, backed = true)
	val alarmFrequency = int("alarmFrequency", 0, backed = true)
	val theme = int("theme", defValue = 0, backed = true)
	val lastTab = string("lastTab", defValue = Screen.Updates.route, backed = true)
}
