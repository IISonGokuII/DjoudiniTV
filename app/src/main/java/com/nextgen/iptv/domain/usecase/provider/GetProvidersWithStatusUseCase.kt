package com.nextgen.iptv.domain.usecase.provider

import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import com.nextgen.iptv.ui.viewmodel.ProviderStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetProvidersWithStatusUseCase @Inject constructor(
    private val providerRepository: ProviderRepository,
    private val streamRepository: StreamRepository
) {
    operator fun invoke(): Flow<List<ProviderStatus>> {
        return combine(
            providerRepository.getAllProviders(),
            streamRepository.getAllStreams()
        ) { providers, streams ->
            providers.map { provider ->
                val providerStreams = streams.filter { it.providerId == provider.id }
                ProviderStatus(
                    id = provider.id,
                    name = provider.name,
                    type = provider.type,
                    isConnected = provider.lastSync != null,
                    channelCount = providerStreams.size,
                    lastSync = provider.lastSync
                )
            }
        }
    }
}
