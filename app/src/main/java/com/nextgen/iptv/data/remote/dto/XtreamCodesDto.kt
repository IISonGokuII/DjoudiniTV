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

// VOD Info (detailed)
@Serializable
data class XtreamVodInfo(
    @SerialName("info") val info: XtreamVodInfoDetail? = null,
    @SerialName("movie_data") val movieData: XtreamVodMovieData? = null
)

@Serializable
data class XtreamVodInfoDetail(
    @SerialName("kinopoisk_url") val kinopoiskUrl: String? = null,
    @SerialName("tmdb_id") val tmdbId: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("o_name") val originalName: String? = null,
    @SerialName("cover_big") val coverBig: String? = null,
    @SerialName("movie_image") val movieImage: String? = null,
    @SerialName("releasedate") val releaseDate: String? = null,
    @SerialName("episode_run_time") val episodeRunTime: String? = null,
    @SerialName("youtube_trailer") val youtubeTrailer: String? = null,
    @SerialName("director") val director: String? = null,
    @SerialName("actors") val actors: String? = null,
    @SerialName("cast") val cast: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("plot") val plot: String? = null,
    @SerialName("age") val age: String? = null,
    @SerialName("mpaa_rating") val mpaaRating: String? = null,
    @SerialName("rating_count_kinopoisk") val ratingCountKinopoisk: Int? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("genre") val genre: String? = null,
    @SerialName("backdrop_path") val backdropPath: List<String>? = null,
    @SerialName("duration_secs") val durationSecs: Int? = null,
    @SerialName("duration") val duration: String? = null,
    @SerialName("video") val video: XtreamVodVideo? = null,
    @SerialName("audio") val audio: XtreamVodAudio? = null,
    @SerialName("bitrate") val bitrate: Int? = null,
    @SerialName("rating") val rating: String? = null,
    @SerialName("release_date") val releaseDateAlt: String? = null
)

@Serializable
data class XtreamVodMovieData(
    @SerialName("stream_id") val streamId: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("added") val added: String? = null,
    @SerialName("category_id") val categoryId: String? = null,
    @SerialName("container_extension") val containerExtension: String? = null,
    @SerialName("custom_sid") val customSid: String? = null,
    @SerialName("direct_source") val directSource: String? = null
)

@Serializable
data class XtreamVodVideo(
    @SerialName("index") val index: Int? = null,
    @SerialName("codec_name") val codecName: String? = null,
    @SerialName("codec_long_name") val codecLongName: String? = null,
    @SerialName("profile") val profile: String? = null,
    @SerialName("codec_type") val codecType: String? = null,
    @SerialName("codec_time_base") val codecTimeBase: String? = null,
    @SerialName("codec_tag_string") val codecTagString: String? = null,
    @SerialName("codec_tag") val codecTag: String? = null,
    @SerialName("width") val width: Int? = null,
    @SerialName("height") val height: Int? = null,
    @SerialName("coded_width") val codedWidth: Int? = null,
    @SerialName("coded_height") val codedHeight: Int? = null,
    @SerialName("has_b_frames") val hasBFrames: Int? = null,
    @SerialName("sample_aspect_ratio") val sampleAspectRatio: String? = null,
    @SerialName("display_aspect_ratio") val displayAspectRatio: String? = null,
    @SerialName("pix_fmt") val pixFmt: String? = null,
    @SerialName("level") val level: Int? = null,
    @SerialName("color_range") val colorRange: String? = null,
    @SerialName("color_space") val colorSpace: String? = null,
    @SerialName("color_transfer") val colorTransfer: String? = null,
    @SerialName("color_primaries") val colorPrimaries: String? = null,
    @SerialName("chroma_location") val chromaLocation: String? = null,
    @SerialName("field_order") val fieldOrder: String? = null,
    @SerialName("timecode") val timecode: String? = null,
    @SerialName("refs") val refs: Int? = null,
    @SerialName("is_avc") val isAvc: String? = null,
    @SerialName("nal_length_size") val nalLengthSize: String? = null,
    @SerialName("id") val id: String? = null,
    @SerialName("r_frame_rate") val rFrameRate: String? = null,
    @SerialName("avg_frame_rate") val avgFrameRate: String? = null,
    @SerialName("time_base") val timeBase: String? = null,
    @SerialName("start_pts") val startPts: Long? = null,
    @SerialName("start_time") val startTime: String? = null,
    @SerialName("duration_ts") val durationTs: Long? = null,
    @SerialName("duration") val duration: String? = null,
    @SerialName("bit_rate") val bitRate: String? = null,
    @SerialName("bits_per_raw_sample") val bitsPerRawSample: String? = null,
    @SerialName("nb_frames") val nbFrames: String? = null,
    @SerialName("nb_read_frames") val nbReadFrames: String? = null,
    @SerialName("nb_read_packets") val nbReadPackets: String? = null,
    @SerialName("tags") val tags: XtreamVodTags? = null
)

@Serializable
data class XtreamVodAudio(
    @SerialName("index") val index: Int? = null,
    @SerialName("codec_name") val codecName: String? = null,
    @SerialName("codec_long_name") val codecLongName: String? = null,
    @SerialName("profile") val profile: String? = null,
    @SerialName("codec_type") val codecType: String? = null,
    @SerialName("codec_time_base") val codecTimeBase: String? = null,
    @SerialName("codec_tag_string") val codecTagString: String? = null,
    @SerialName("codec_tag") val codecTag: String? = null,
    @SerialName("sample_fmt") val sampleFmt: String? = null,
    @SerialName("sample_rate") val sampleRate: String? = null,
    @SerialName("channels") val channels: Int? = null,
    @SerialName("channel_layout") val channelLayout: String? = null,
    @SerialName("bits_per_sample") val bitsPerSample: Int? = null,
    @SerialName("id") val id: String? = null,
    @SerialName("r_frame_rate") val rFrameRate: String? = null,
    @SerialName("avg_frame_rate") val avgFrameRate: String? = null,
    @SerialName("time_base") val timeBase: String? = null,
    @SerialName("start_pts") val startPts: Long? = null,
    @SerialName("start_time") val startTime: String? = null,
    @SerialName("duration_ts") val durationTs: Long? = null,
    @SerialName("duration") val duration: String? = null,
    @SerialName("bit_rate") val bitRate: String? = null,
    @SerialName("max_bit_rate") val maxBitRate: String? = null,
    @SerialName("nb_frames") val nbFrames: String? = null,
    @SerialName("nb_read_frames") val nbReadFrames: String? = null,
    @SerialName("nb_read_packets") val nbReadPackets: String? = null,
    @SerialName("tags") val tags: XtreamVodTags? = null
)

@Serializable
data class XtreamVodTags(
    @SerialName("language") val language: String? = null,
    @SerialName("handler_name") val handlerName: String? = null,
    @SerialName("creation_time") val creationTime: String? = null,
    @SerialName("encoder") val encoder: String? = null
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
    @SerialName("director") val director: String? = null,
    @SerialName("genre") val genre: String? = null,
    @SerialName("releaseDate") val releaseDate: String? = null,
    @SerialName("rating") val rating: String? = null,
    @SerialName("rating_5based") val rating5Based: Double? = null,
    @SerialName("backdrop_path") val backdropPath: List<String>? = null,
    @SerialName("episode_run_time") val episodeRunTime: String? = null,
    @SerialName("youtube_trailer") val youtubeTrailer: String? = null,
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
    @SerialName("cover_big") val coverBig: String? = null,
    @SerialName("overview") val overview: String? = null
)

@Serializable
data class XtreamEpisode(
    @SerialName("id") val id: String? = null,
    @SerialName("episode_num") val episodeNum: Int? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("container_extension") val containerExtension: String? = null,
    @SerialName("info") val info: XtreamEpisodeInfo? = null,
    @SerialName("season") val season: String? = null,
    @SerialName("added") val added: String? = null
)

@Serializable
data class XtreamEpisodeInfo(
    @SerialName("movie_image") val movieImage: String? = null,
    @SerialName("plot") val plot: String? = null,
    @SerialName("releasedate") val releaseDate: String? = null,
    @SerialName("duration") val duration: String? = null,
    @SerialName("duration_secs") val durationSecs: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("rating") val rating: String? = null
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
