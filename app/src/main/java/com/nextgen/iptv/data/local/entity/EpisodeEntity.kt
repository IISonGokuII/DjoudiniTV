package com.nextgen.iptv.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "episodes",
    indices = [Index("seriesId"), Index("seasonId")]
)
data class EpisodeEntity(
    @PrimaryKey val id: String,
    val seriesId: String,
    val seasonId: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val name: String,
    val plot: String? = null,
    val posterUrl: String? = null,
    val durationSec: Int? = null,
    val streamUrl: String,
    val containerExtension: String? = null,
    val added: String? = null,
    val rating: String? = null
)
