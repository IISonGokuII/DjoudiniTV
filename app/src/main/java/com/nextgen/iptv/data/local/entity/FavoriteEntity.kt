package com.nextgen.iptv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val streamId: String,
    val streamType: String,
    val addedAt: Long = System.currentTimeMillis()
)
