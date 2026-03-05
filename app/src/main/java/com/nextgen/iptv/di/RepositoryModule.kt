package com.nextgen.iptv.di

import com.nextgen.iptv.data.repository.CategoryRepositoryImpl
import com.nextgen.iptv.data.repository.ProviderRepositoryImpl
import com.nextgen.iptv.data.repository.StreamRepositoryImpl
import com.nextgen.iptv.domain.repository.CategoryRepository
import com.nextgen.iptv.domain.repository.ProviderRepository
import com.nextgen.iptv.domain.repository.StreamRepository
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
}
