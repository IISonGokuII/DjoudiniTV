package com.nextgen.iptv.domain.usecase.settings

import com.nextgen.iptv.domain.model.AppSettings
import com.nextgen.iptv.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(settings: AppSettings) {
        settingsRepository.updateSettings(settings)
    }
}
