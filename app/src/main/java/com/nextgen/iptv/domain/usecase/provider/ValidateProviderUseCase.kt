package com.nextgen.iptv.domain.usecase.provider

import com.nextgen.iptv.data.remote.api.XtreamCodesService
import com.nextgen.iptv.domain.model.ProviderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed class ValidationResult {
    data class Success(val info: String) : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

class ValidateProviderUseCase @Inject constructor(
    private val xtreamCodesService: XtreamCodesService
) {
    suspend operator fun invoke(type: ProviderType): ValidationResult = withContext(Dispatchers.IO) {
        try {
            withTimeout(30000) { // 30 Sekunden Timeout
                when (type) {
                    is ProviderType.XtreamCodes -> validateXtreamCodes(type)
                    is ProviderType.M3uUrl -> ValidationResult.Success("M3U URL akzeptiert")
                    is ProviderType.M3uLocal -> ValidationResult.Error("Lokale M3U noch nicht unterstützt")
                }
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> ValidationResult.Error("Keine Internetverbindung")
                is HttpException -> ValidationResult.Error("Server-Fehler: ${e.code()}")
                else -> ValidationResult.Error(e.message ?: "Unbekannter Fehler")
            }
        }
    }
    
    private suspend fun validateXtreamCodes(type: ProviderType.XtreamCodes): ValidationResult {
        val api = xtreamCodesService.createApi(type.serverUrl)
        val apiUrl = XtreamCodesService.buildApiUrl(type.serverUrl)
        
        return try {
            val auth = api.authenticate(apiUrl, type.username, type.password)
            
            when {
                auth.userInfo?.auth != 1 -> ValidationResult.Error("Anmeldung fehlgeschlagen - Falsche Zugangsdaten")
                else -> {
                    val status = auth.userInfo.status ?: "Active"
                    val expDate = auth.userInfo.expDate?.let { 
                        val date = java.util.Date(it.toLong() * 1000)
                        java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.GERMAN).format(date)
                    } ?: "Unbegrenzt"
                    
                    val maxConnections = auth.userInfo.maxConnections ?: "Unbegrenzt"
                    
                    ValidationResult.Success(
                        "Account aktiv bis $expDate | Max. Verbindungen: $maxConnections"
                    )
                }
            }
        } catch (e: Exception) {
            ValidationResult.Error("Verbindung fehlgeschlagen: ${e.message}")
        }
    }
}
