package com.nextgen.iptv.domain.model

sealed class ProviderType {
    data class XtreamCodes(
        val serverUrl: String,
        val username: String,
        val password: String
    ) : ProviderType()
    
    data class M3UUrl(
        val url: String
    ) : ProviderType()
    
    data class M3ULocal(
        val filePath: String
    ) : ProviderType()
}

fun ProviderType.getDisplayName(): String = when (this) {
    is ProviderType.XtreamCodes -> "Xtream Codes API"
    is ProviderType.M3UUrl -> "M3U URL"
    is ProviderType.M3ULocal -> "Lokale M3U Datei"
}
