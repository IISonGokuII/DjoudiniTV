package com.nextgen.iptv.di

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.nextgen.iptv.data.player.ExoPlayerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PlayerModule {
    
    @Provides
    @ViewModelScoped
    @UnstableApi
    fun provideExoPlayerManager(
        @ApplicationContext context: Context,
        okHttpClient: okhttp3.OkHttpClient
    ): ExoPlayerManager {
        return ExoPlayerManager(context, okHttpClient)
    }
}
