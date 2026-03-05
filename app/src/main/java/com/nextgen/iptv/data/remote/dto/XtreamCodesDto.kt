package com.nextgen.iptv.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Authentication Response
@Serializable
data class XtreamAuthResponse(
    @SerialName("user_info") val userInfo: XtreamUserInfo? = null,
    @SerialName("server_info") val serverInfo: XtreamServerInfo? = null,
    val status: String? = null
)

@Serializable
data class XtreamUserInfo(
    @SerialName("username") val username: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("auth") val auth: Int? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("exp_date") val expDate: String? = null,
    @SerialName("is_trial") val isTrial: String? = null,
    @SerialName("active_cons") val activeCons: Int? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("max_connections") val maxConnections: String? = null
)

@Serializable
data class XtreamServerInfo(
    @SerialName("url") val url: String? = null,
    @SerialName("port") val port: String? = null,
    @SerialName("https_port") val httpsPort: String? = null,
    @SerialName("server_protocol") val serverProtocol: String? = null,
    @SerialName("rtmp_port") val rtmpPort: String? = null,
    @SerialName("timezone") val timezone: String? = null,
    @SerialName("timestamp_now") val timestampNow: Long? = null,
    @SerialName("time_now") val timeNow: String? = null
)

// Live Streams
@Serializable
data class XtreamLiveStream(
    @SerialName("num") val num: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("stream_type") val streamType: String? = null,
    @SerialName("stream_id") val streamId: Int? = null,
    @SerialName("stream_icon") val streamIcon: String? = null,
    @SerialName("epg_channel_id") val epgChannelId: String? = null,
    @SerialName("added") val added: String? = null,
    @SerialName("category_id") val categoryId: String? = null,
    @SerialName("custom_sid") val customSid: String? = null,
    @SerialName("tv_archive") val tvArchive: Int? = null,
    @SerialName("direct_source") val directSource: String? = null,
    @SerialName("tv_archive_duration") val tvArchiveDuration: Int? = null
)

// VOD Streams
@Serializable
data class XtreamVodStream(
    @SerialName("num") val num: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("stream_type") val streamType: String? = null,
    @SerialName("stream_id") val streamId: Int? = null,
    @SerialName("stream_icon") val streamIcon: String? = null,
    @SerialName("rating") val rating: String? = null,
    @SerialName("rating_5based") val rating5Based: Double? = null,
    @SerialName("added") val added: String? = null,
    @SerialName("category_id") val categoryId: String? = null,
    @SerialName("container_extension") val containerExtension: String? = null,
    @SerialName("custom_sid") val customSid: String? = null,
    @SerialName("direct_source") val directSource: String? = null
)

// Series
@Serializable
data class XtreamSeries(
    @SerialName("num") val num: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("series_id") val seriesId: Int? = null,
    @SerialName("cover") val cover: String? = null,
    @SerialName("plot") val plot: String? = null,
    @SerialName("cast") val cast: String? = null,
    @SerialName("rating") val rating: String? = null,
    @SerialName("rating_5based") val rating5Based: Double? = null,
    @SerialName("added") val added: String? = null,
    @SerialName("category_id") val categoryId: String? = null
)

// Series Info
@Serializable
data class XtreamSeriesInfo(
    @SerialName("seasons") val seasons: List<XtreamSeason>? = null,
    @SerialName("episodes") val episodes: Map<String, List<XtreamEpisode>>? = null,
    @SerialName("info") val info: XtreamSeriesInfoDetail? = null
)

@Serializable
data class XtreamSeason(
    @SerialName("name") val name: String? = null,
    @SerialName("air_date") val airDate: String? = null,
    @SerialName("episode_count") val episodeCount: Int? = null,
    @SerialName("season_number") val seasonNumber: Int? = null,
    @SerialName("cover") val cover: String? = null,
    @SerialName("overview") val overview: String? = null
)

@Serializable
data class XtreamEpisode(
    @SerialName("id") val id: String? = null,
    @SerialName("episode_num") val episodeNum: Int? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("container_extension") val containerExtension: String? = null,
    @SerialName("info") val info: XtreamEpisodeInfo? = null
)

@Serializable
data class XtreamEpisodeInfo(
    @SerialName("movie_image") val movieImage: String? = null,
    @SerialName("plot") val plot: String? = null,
    @SerialName("releasedate") val releaseDate: String? = null,
    @SerialName("duration") val duration: String? = null,
    @SerialName("duration_secs") val durationSecs: Int? = null
)

@Serializable
data class XtreamSeriesInfoDetail(
    @SerialName("name") val name: String? = null,
    @SerialName("cover") val cover: String? = null,
    @SerialName("plot") val plot: String? = null,
    @SerialName("cast") val cast: String? = null,
    @SerialName("rating") val rating: String? = null,
    @SerialName("genre") val genre: String? = null
)

// Categories
@Serializable
data class XtreamCategory(
    @SerialName("category_id") val categoryId: String? = null,
    @SerialName("category_name") val categoryName: String? = null,
    @SerialName("parent_id") val parentId: Int? = null
)
