package com.nextgen.iptv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "providers")
data class ProviderEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,       // "xtream" | "m3u_url" | "m3u_local"
    val serverUrl: String,
    val username: String?,
    val password: String?,
    val lastSync: Long = 0L
)
