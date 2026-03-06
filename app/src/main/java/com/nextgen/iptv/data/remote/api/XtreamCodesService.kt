package com.nextgen.iptv.data.remote.api

import com.nextgen.iptv.data.remote.dto.XtreamAuthResponse
import com.nextgen.iptv.data.remote.dto.XtreamCategory
import com.nextgen.iptv.data.remote.dto.XtreamLiveStream
import com.nextgen.iptv.data.remote.dto.XtreamSeries
import com.nextgen.iptv.data.remote.dto.XtreamSeriesInfo
import com.nextgen.iptv.data.remote.dto.XtreamVodInfo
import com.nextgen.iptv.data.remote.dto.XtreamVodStream
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import javax.inject.Inject

/**
 * Interface for Xtream Codes API calls with dynamic base URLs.
 */
interface XtreamCodesApiDynamic {
    
    @GET
    suspend fun authenticate(
        @Url url: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): XtreamAuthResponse
    
    @GET
    suspend fun getLiveStreams(
        @Url url: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_live_streams"
    ): List<XtreamLiveStream>
    
    @GET
    suspend fun getLiveCategories(
        @Url url: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_live_categories"
    ): List<XtreamCategory>
    
    @GET
    suspend fun getVodStreams(
        @Url url: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_vod_streams"
    ): List<XtreamVodStream>
    
    @GET
    suspend fun getVodCategories(
        @Url url: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_vod_categories"
    ): List<XtreamCategory>
    
    @GET
    suspend fun getSeries(
        @Url url: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_series"
    ): List<XtreamSeries>
    
    @GET
    suspend fun getSeriesCategories(
        @Url url: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_series_categories"
    ): List<XtreamCategory>
    
    @GET
    suspend fun getSeriesInfo(
        @Url url: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_series_info",
        @Query("series_id") seriesId: String
    ): XtreamSeriesInfo
    
    @GET
    suspend fun getVodInfo(
        @Url url: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_vod_info",
        @Query("vod_id") vodId: String
    ): XtreamVodInfo
}

/**
 * Service class that creates API instances with the correct base URL.
 */
class XtreamCodesService @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val json: Json
) {
    
    fun createApi(baseUrl: String): XtreamCodesApiDynamic {
        val sanitizedUrl = if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/"
        
        val retrofit = Retrofit.Builder()
            .baseUrl(sanitizedUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        
        return retrofit.create(XtreamCodesApiDynamic::class.java)
    }
    
    companion object {
        fun buildApiUrl(baseUrl: String): String {
            val sanitized = baseUrl.trim().removeSuffix("/")
            return "$sanitized/player_api.php"
        }
        
        fun buildStreamUrl(baseUrl: String, username: String, password: String, streamId: String, extension: String = "ts"): String {
            val sanitized = baseUrl.trim().removeSuffix("/")
            return "$sanitized/live/$username/$password/$streamId.$extension"
        }
        
        fun buildVodUrl(baseUrl: String, username: String, password: String, streamId: String, extension: String = "mp4"): String {
            val sanitized = baseUrl.trim().removeSuffix("/")
            return "$sanitized/movie/$username/$password/$streamId.$extension"
        }
        
        fun buildSeriesUrl(baseUrl: String, username: String, password: String, streamId: String, extension: String = "mp4"): String {
            val sanitized = baseUrl.trim().removeSuffix("/")
            return "$sanitized/series/$username/$password/$streamId.$extension"
        }
    }
}
