package com.nextgen.iptv.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "streams",
    indices = [Index("categoryId"), Index("providerId"), Index("epgChannelId")]
)
data class StreamEntity(
    @PrimaryKey val id: String,
    val categoryId: String,
    val providerId: String,
    val name: String,
    val streamUrl: String,
    val logoUrl: String?,
    val epgChannelId: String?,
    val type: String        // "live" | "vod" | "series_episode"
)
