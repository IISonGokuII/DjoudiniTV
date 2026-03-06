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
    val type: String,        // "live" | "vod" | "series_episode"
    // VOD specific fields
    val plot: String? = null,
    val cast: String? = null,
    val director: String? = null,
    val genre: String? = null,
    val rating: String? = null,
    val rating5Based: Double? = null,
    val releaseDate: String? = null,
    val durationSecs: Int? = null,
    val duration: String? = null,
    val backdropUrl: String? = null,
    val youtubeTrailer: String? = null,
    val added: String? = null,
    val containerExtension: String? = null
)
