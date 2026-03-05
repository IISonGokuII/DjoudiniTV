package com.nextgen.iptv.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nextgen.iptv.data.parser.XmlTvParser
import com.nextgen.iptv.domain.repository.EpgRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.net.URL

@HiltWorker
class EpgSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val xmlTvParser: XmlTvParser,
    private val epgRepository: EpgRepository
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        val epgUrl = inputData.getString(KEY_EPG_URL) ?: return Result.failure()
        
        return try {
            withContext(Dispatchers.IO) {
                val url = URL(epgUrl)
                url.openStream().use { stream ->
                    xmlTvParser.parse(stream).collect { batch ->
                        epgRepository.addEvents(batch)
                    }
                }
            }
            
            // Clean old events
            val oneDayAgo = System.currentTimeMillis() - 24 * 60 * 60 * 1000
            epgRepository.deleteOldEvents(oneDayAgo)
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
    
    companion object {
        const val KEY_EPG_URL = "epg_url"
        const val WORK_NAME = "epg_sync_worker"
    }
}
