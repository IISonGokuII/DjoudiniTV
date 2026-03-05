package com.nextgen.iptv.domain.model

sealed class ProviderType {
    data class XtreamCodes(
        val serverUrl: String,
        val username: String,
        val password: String
    ) : ProviderType()
    
    data class M3uUrl(val url: String) : ProviderType()
    data class M3uLocal(val filePath: String) : ProviderType()
}
