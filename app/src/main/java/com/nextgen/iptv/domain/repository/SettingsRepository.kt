package com.nextgen.iptv.domain.repository

import com.nextgen.iptv.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<AppSettings>
    suspend fun updateSettings(settings: AppSettings)
    suspend fun clearAllData()
    
    // Category filters
    fun getSelectedLiveCategories(providerId: String): Flow<Set<String>>
    fun getSelectedVodCategories(providerId: String): Flow<Set<String>>
    fun getSelectedSeriesCategories(providerId: String): Flow<Set<String>>
    
    suspend fun setSelectedLiveCategories(providerId: String, categories: Set<String>)
    suspend fun setSelectedVodCategories(providerId: String, categories: Set<String>)
    suspend fun setSelectedSeriesCategories(providerId: String, categories: Set<String>)
    
    suspend fun clearCategoryFilters(providerId: String)
}
