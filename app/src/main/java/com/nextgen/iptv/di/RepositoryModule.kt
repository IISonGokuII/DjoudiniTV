package com.nextgen.iptv.di

import com.nextgen.iptv.data.repository.CategoryRepositoryImpl
import com.nextgen.iptv.data.repository.EpgRepositoryImpl
import com.nextgen.iptv.data.repository.FavoriteRepositoryImpl
import com.nextgen.iptv.data.repository.ProviderRepositoryImpl
import com.nextgen.iptv.data.repository.SeriesRepositoryImpl
import com.nextgen.iptv.data.repository.SettingsRepositoryImpl
import com.nextgen.iptv.data.repository.StreamRepositoryImpl
import com.nextgen.iptv.data.repository.VodProgressRepositoryImpl
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.EpgRepository
import com.nextgen.iptv.domain.repository.FavoriteRepository
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.SeriesRepository
import com.nextgen.iptv.domain.repository.SettingsRepository
import com.nextgen.iptv.domain.repository.StreamRepository
import com.nextgen.iptv.domain.repository.VodProgressRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    abstract fun bindProviderRepository(
        impl: ProviderRepositoryImpl
    ): ProviderRepository
    
    @Binds
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository
    
    @Binds
    abstract fun bindStreamRepository(
        impl: StreamRepositoryImpl
    ): StreamRepository
    
    @Binds
    abstract fun bindEpgRepository(
        impl: EpgRepositoryImpl
    ): EpgRepository
    
    @Binds
    abstract fun bindVodProgressRepository(
        impl: VodProgressRepositoryImpl
    ): VodProgressRepository
    
    @Binds
    abstract fun bindFavoriteRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository
    
    @Binds
    abstract fun bindSeriesRepository(
        impl: SeriesRepositoryImpl
    ): SeriesRepository
    
    @Binds
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository
}
