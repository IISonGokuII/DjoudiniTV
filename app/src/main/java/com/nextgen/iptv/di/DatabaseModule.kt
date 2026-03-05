package com.nextgen.iptv.di

import android.content.Context
import androidx.room.Room
import com.nextgen.iptv.data.local.AppDatabase
import com.nextgen.iptv.data.repository.CategoryRepositoryImpl
import com.nextgen.iptv.data.repository.EpgRepositoryImpl
import com.nextgen.iptv.data.repository.ProviderRepositoryImpl
import com.nextgen.iptv.data.repository.StreamRepositoryImpl
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.EpgRepository
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.StreamRepository
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
    fun provideEpgDao(database: AppDatabase) = database.epgDao()
    
    @Provides
    fun provideVodProgressDao(database: AppDatabase) = database.vodProgressDao()
    
    @Provides
    fun provideFavoriteDao(database: AppDatabase) = database.favoriteDao()
    
    @Provides
    @Singleton
    fun provideProviderRepository(impl: ProviderRepositoryImpl): ProviderRepository = impl
    
    @Provides
    @Singleton
    fun provideCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository = impl
    
    @Provides
    @Singleton
    fun provideStreamRepository(impl: StreamRepositoryImpl): StreamRepository = impl
    
    @Provides
    @Singleton
    fun provideEpgRepository(impl: EpgRepositoryImpl): EpgRepository = impl
}
