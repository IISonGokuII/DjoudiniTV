package com.nextgen.iptv.domain.usecase.settings

import com.nextgen.iptv.domain.model.AppSettings
import com.nextgen.iptv.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<AppSettings> {
        return settingsRepository.getSettings()
    }
}
