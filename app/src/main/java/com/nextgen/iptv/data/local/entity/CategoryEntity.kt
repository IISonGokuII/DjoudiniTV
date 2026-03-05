package com.nextgen.iptv.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    indices = [Index("providerId"), Index("type")]
)
data class CategoryEntity(
    @PrimaryKey val id: String,
    val providerId: String,
    val name: String,
    val type: String        // "live" | "vod" | "series"
)
