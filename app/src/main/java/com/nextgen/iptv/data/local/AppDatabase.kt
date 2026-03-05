package com.nextgen.iptv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nextgen.iptv.data.local.dao.CategoryDao
import com.nextgen.iptv.data.local.dao.EpgEventDao
import com.nextgen.iptv.data.local.dao.FavoriteDao
import com.nextgen.iptv.data.local.dao.ProviderDao
import com.nextgen.iptv.data.local.dao.StreamDao
import com.nextgen.iptv.data.local.dao.VodProgressDao
import com.nextgen.iptv.data.local.entity.CategoryEntity
import com.nextgen.iptv.data.local.entity.EpgEventEntity
import com.nextgen.iptv.data.local.entity.FavoriteEntity
import com.nextgen.iptv.data.local.entity.ProviderEntity
import com.nextgen.iptv.data.local.entity.StreamEntity
import com.nextgen.iptv.data.local.entity.VodProgressEntity

@Database(
    entities = [
        ProviderEntity::class,
        CategoryEntity::class,
        StreamEntity::class,
        EpgEventEntity::class,
        VodProgressEntity::class,
        FavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun providerDao(): ProviderDao
    abstract fun categoryDao(): CategoryDao
    abstract fun streamDao(): StreamDao
    abstract fun epgEventDao(): EpgEventDao
    abstract fun vodProgressDao(): VodProgressDao
    abstract fun favoriteDao(): FavoriteDao
}
