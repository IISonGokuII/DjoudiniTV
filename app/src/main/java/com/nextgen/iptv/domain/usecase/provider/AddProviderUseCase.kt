package com.nextgen.iptv.domain.usecase.provider

import com.nextgen.iptv.data.local.entity.ProviderEntity
import com.nextgen.iptv.domain.model.ProviderType
import com.nextgen.iptv.domain.repository.ProviderRepository
import java.util.UUID
import javax.inject.Inject

class AddProviderUseCase @Inject constructor(
    private val providerRepository: ProviderRepository
) {
    suspend operator fun invoke(name: String, type: ProviderType): Result<String> {
        return try {
            val id = UUID.randomUUID().toString()
            val provider = when (type) {
                is ProviderType.XtreamCodes -> ProviderEntity(
                    id = id,
                    name = name,
                    type = "xtream",
                    serverUrl = type.serverUrl,
                    username = type.username,
                    password = type.password
                )
                is ProviderType.M3uUrl -> ProviderEntity(
                    id = id,
                    name = name,
                    type = "m3u_url",
                    serverUrl = type.url,
                    username = null,
                    password = null
                )
                is ProviderType.M3uLocal -> ProviderEntity(
                    id = id,
                    name = name,
                    type = "m3u_local",
                    serverUrl = type.filePath,
                    username = null,
                    password = null
                )
            }
            providerRepository.insertProvider(provider)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
