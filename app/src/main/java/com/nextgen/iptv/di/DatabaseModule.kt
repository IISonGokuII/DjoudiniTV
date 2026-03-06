package com.nextgen.iptv.di

import android.content.Context
import androidx.room.Room
import com.nextgen.iptv.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "djoundinitv.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    fun provideProviderDao(database: AppDatabase) = database.providerDao()
    
    @Provides
    fun provideCategoryDao(database: AppDatabase) = database.categoryDao()
    
    @Provides
    fun provideStreamDao(database: AppDatabase) = database.streamDao()
    
    @Provides
    fun provideEpgDao(database: AppDatabase) = database.epgEventDao()
    
    @Provides
    fun provideVodProgressDao(database: AppDatabase) = database.vodProgressDao()
    
    @Provides
    fun provideFavoriteDao(database: AppDatabase) = database.favoriteDao()
    
    @Provides
    fun provideSeriesDao(database: AppDatabase) = database.seriesDao()
}
