package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<AppSettings>
    suspend fun updateSettings(settings: AppSettings)
    suspend fun clearAllData()
}
