package com.nextgen.iptv.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.nextgen.iptv.domain.model.AppSettings
import com.nextgen.iptv.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _settingsFlow = MutableStateFlow(loadSettings())

    override fun getSettings(): Flow<AppSettings> = _settingsFlow.asStateFlow()

    override suspend fun updateSettings(settings: AppSettings) {
        prefs.edit {
            putInt(KEY_BUFFER_SIZE, settings.playerBufferSizeMs)
            putInt(KEY_EPG_INTERVAL, settings.epgRefreshIntervalHours)
            putBoolean(KEY_DARK_MODE, settings.useDarkMode)
            putString(KEY_STREAM_FORMAT, settings.preferredStreamFormat)
            putBoolean(KEY_AUTO_UPDATE, settings.autoUpdateOnWifi)
        }
        _settingsFlow.value = settings
    }

    override suspend fun clearAllData() {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply()
        // Note: Database clearing should be handled separately
    }
    
    // Category Filters
    override fun getSelectedLiveCategories(providerId: String): Flow<Set<String>> {
        val key = "${KEY_LIVE_CATS}_$providerId"
        val cats = prefs.getStringSet(key, emptySet()) ?: emptySet()
        return MutableStateFlow(cats).asStateFlow()
    }
    
    override fun getSelectedVodCategories(providerId: String): Flow<Set<String>> {
        val key = "${KEY_VOD_CATS}_$providerId"
        val cats = prefs.getStringSet(key, emptySet()) ?: emptySet()
        return MutableStateFlow(cats).asStateFlow()
    }
    
    override fun getSelectedSeriesCategories(providerId: String): Flow<Set<String>> {
        val key = "${KEY_SERIES_CATS}_$providerId"
        val cats = prefs.getStringSet(key, emptySet()) ?: emptySet()
        return MutableStateFlow(cats).asStateFlow()
    }
    
    override suspend fun setSelectedLiveCategories(providerId: String, categories: Set<String>) {
        prefs.edit { putStringSet("${KEY_LIVE_CATS}_$providerId", categories) }
    }
    
    override suspend fun setSelectedVodCategories(providerId: String, categories: Set<String>) {
        prefs.edit { putStringSet("${KEY_VOD_CATS}_$providerId", categories) }
    }
    
    override suspend fun setSelectedSeriesCategories(providerId: String, categories: Set<String>) {
        prefs.edit { putStringSet("${KEY_SERIES_CATS}_$providerId", categories) }
    }
    
    override suspend fun clearCategoryFilters(providerId: String) {
        prefs.edit {
            remove("${KEY_LIVE_CATS}_$providerId")
            remove("${KEY_VOD_CATS}_$providerId")
            remove("${KEY_SERIES_CATS}_$providerId")
        }
    }

    private fun loadSettings(): AppSettings {
        return AppSettings(
            playerBufferSizeMs = prefs.getInt(KEY_BUFFER_SIZE, 1000),
            epgRefreshIntervalHours = prefs.getInt(KEY_EPG_INTERVAL, 24),
            useDarkMode = prefs.getBoolean(KEY_DARK_MODE, true),
            preferredStreamFormat = prefs.getString(KEY_STREAM_FORMAT, "ts") ?: "ts",
            autoUpdateOnWifi = prefs.getBoolean(KEY_AUTO_UPDATE, true)
        )
    }

    companion object {
        private const val PREFS_NAME = "djoundinitv_settings"
        private const val KEY_BUFFER_SIZE = "player_buffer_size"
        private const val KEY_EPG_INTERVAL = "epg_refresh_interval"
        private const val KEY_DARK_MODE = "use_dark_mode"
        private const val KEY_STREAM_FORMAT = "stream_format"
        private const val KEY_AUTO_UPDATE = "auto_update_wifi"
        
        private const val KEY_LIVE_CATS = "selected_live_categories"
        private const val KEY_VOD_CATS = "selected_vod_categories"
        private const val KEY_SERIES_CATS = "selected_series_categories"
    }
}
