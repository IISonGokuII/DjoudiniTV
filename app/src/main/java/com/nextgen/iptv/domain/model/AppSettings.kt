package com.nextgen.iptv.domain.model

data class AppSettings(
    val playerBufferSizeMs: Int = 1000,
    val epgRefreshIntervalHours: Int = 24,
    val useDarkMode: Boolean = true,
    val preferredStreamFormat: String = "ts",
    val autoUpdateOnWifi: Boolean = true
)
