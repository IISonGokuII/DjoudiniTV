package com.nextgen.iptv.data.parser

import com.nextgen.iptv.data.local.entity.EpgEventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XmlTvParser @Inject constructor() {
    
    private val dateFormat = SimpleDateFormat("yyyyMMddHHmmss Z", Locale.US)
    
    fun parse(inputStream: InputStream): Flow<List<EpgEventEntity>> = flow {
        val batch = mutableListOf<EpgEventEntity>()
        var currentChannelId: String? = null
        var currentTitle: String = ""
        var currentDesc: String? = null
        var currentStart: Long = 0L
        var currentEnd: Long = 0L
        
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(inputStream, "UTF-8")
        
        var eventType = parser.eventType
        
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "programme" -> {
                            currentChannelId = parser.getAttributeValue(null, "channel")
                            currentStart = parseDate(parser.getAttributeValue(null, "start"))
                            currentEnd = parseDate(parser.getAttributeValue(null, "stop"))
                            currentTitle = ""
                            currentDesc = null
                        }
                        "title" -> {
                            currentTitle = parser.nextText() ?: ""
                        }
                        "desc" -> {
                            currentDesc = parser.nextText()
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "programme" && currentChannelId != null) {
                        batch.add(
                            EpgEventEntity(
                                id = UUID.randomUUID().toString(),
                                channelId = currentChannelId,
                                title = currentTitle.ifBlank { "No Title" },
                                description = currentDesc,
                                startTime = currentStart,
                                endTime = currentEnd
                            )
                        )
                        
                        if (batch.size >= BATCH_SIZE) {
                            emit(batch.toList())
                            batch.clear()
                        }
                    }
                }
            }
            eventType = parser.next()
        }
        
        if (batch.isNotEmpty()) {
            emit(batch.toList())
        }
    }.flowOn(Dispatchers.IO)
    
    private fun parseDate(dateString: String?): Long {
        if (dateString == null) return 0L
        return try {
            dateFormat.parse(dateString)?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
    
    companion object {
        private const val BATCH_SIZE = 500
    }
}
