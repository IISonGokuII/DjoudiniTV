package com.nextgen.iptv.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "seasons",
    indices = [Index("seriesId")]
)
data class SeasonEntity(
    @PrimaryKey val id: String,
    val seriesId: String,
    val seasonNumber: Int,
    val name: String,
    val episodeCount: Int = 0,
    val posterUrl: String? = null
)
