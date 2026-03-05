package com.nextgen.iptv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vod_progress")
data class VodProgressEntity(
    @PrimaryKey val streamId: String,
    val progressMs: Long,
    val durationMs: Long,
    val lastWatched: Long
)
