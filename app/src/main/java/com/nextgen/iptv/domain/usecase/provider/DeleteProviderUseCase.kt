package com.nextgen.iptv.domain.usecase.provider

import com.nextgen.iptv.domain.repository.ProviderRepository
import javax.inject.Inject

class DeleteProviderUseCase @Inject constructor(
    private val providerRepository: ProviderRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return try {
            providerRepository.deleteProvider(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
