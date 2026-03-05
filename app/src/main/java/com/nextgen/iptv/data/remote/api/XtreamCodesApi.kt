package com.nextgen.iptv.data.remote.api

import com.nextgen.iptv.data.remote.dto.XtreamAuthResponse
import com.nextgen.iptv.data.remote.dto.XtreamCategory
import com.nextgen.iptv.data.remote.dto.XtreamEpisode
import com.nextgen.iptv.data.remote.dto.XtreamLiveStream
import com.nextgen.iptv.data.remote.dto.XtreamSeries
import com.nextgen.iptv.data.remote.dto.XtreamSeriesInfo
import com.nextgen.iptv.data.remote.dto.XtreamVodStream
import retrofit2.http.GET
import retrofit2.http.Query

interface XtreamCodesApi {
    
    // Authentication
    @GET("player_api.php")
    suspend fun authenticate(
        @Query("username") username: String,
        @Query("password") password: String
    ): XtreamAuthResponse
    
    // Live Streams
    @GET("player_api.php")
    suspend fun getLiveStreams(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_live_streams"
    ): List<XtreamLiveStream>
    
    @GET("player_api.php")
    suspend fun getLiveStreamsByCategory(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_live_streams",
        @Query("category_id") categoryId: String
    ): List<XtreamLiveStream>
    
    // VOD Streams
    @GET("player_api.php")
    suspend fun getVodStreams(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_vod_streams"
    ): List<XtreamVodStream>
    
    @GET("player_api.php")
    suspend fun getVodStreamsByCategory(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_vod_streams",
        @Query("category_id") categoryId: String
    ): List<XtreamVodStream>
    
    // Series
    @GET("player_api.php")
    suspend fun getSeries(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_series"
    ): List<XtreamSeries>
    
    @GET("player_api.php")
    suspend fun getSeriesByCategory(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_series",
        @Query("category_id") categoryId: String
    ): List<XtreamSeries>
    
    @GET("player_api.php")
    suspend fun getSeriesInfo(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_series_info",
        @Query("series_id") seriesId: String
    ): XtreamSeriesInfo
    
    // Categories
    @GET("player_api.php")
    suspend fun getLiveCategories(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_live_categories"
    ): List<XtreamCategory>
    
    @GET("player_api.php")
    suspend fun getVodCategories(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_vod_categories"
    ): List<XtreamCategory>
    
    @GET("player_api.php")
    suspend fun getSeriesCategories(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_series_categories"
    ): List<XtreamCategory>
    
    companion object {
        fun buildStreamUrl(baseUrl: String, username: String, password: String, streamId: String, extension: String = "ts"): String {
            return "$baseUrl/live/$username/$password/$streamId.$extension"
        }
        
        fun buildVodUrl(baseUrl: String, username: String, password: String, streamId: String, extension: String = "mp4"): String {
            return "$baseUrl/movie/$username/$password/$streamId.$extension"
        }
        
        fun buildSeriesUrl(baseUrl: String, username: String, password: String, streamId: String, extension: String = "mp4"): String {
            return "$baseUrl/series/$username/$password/$streamId.$extension"
        }
    }
}
