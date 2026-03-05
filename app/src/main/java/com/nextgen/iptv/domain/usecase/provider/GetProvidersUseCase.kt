package com.nextgen.iptv.domain.usecase.provider

import com.nextgen.iptv.data.local.entity.ProviderEntity
import com.nextgen.iptv.domain.repository.ProviderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProvidersUseCase @Inject constructor(
    private val providerRepository: ProviderRepository
) {
    operator fun invoke(): Flow<List<ProviderEntity>> {
        return providerRepository.getAllProviders()
    }
}
