package com.nextgen.iptv.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "series",
    indices = [Index("providerId"), Index("categoryId")]
)
data class SeriesEntity(
    @PrimaryKey val id: String,
    val providerId: String,
    val categoryId: String,
    val name: String,
    val plot: String? = null,
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val rating: String? = null,
    val releaseDate: String? = null,
    val genre: String? = null,
    val cast: String? = null,
    val director: String? = null,
    val episodeRunTime: String? = null,
    val totalSeasons: Int = 0,
    val totalEpisodes: Int = 0
)
