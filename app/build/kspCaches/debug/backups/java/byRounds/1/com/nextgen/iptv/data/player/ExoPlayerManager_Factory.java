package com.nextgen.iptv.data.player;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class ExoPlayerManager_Factory implements Factory<ExoPlayerManager> {
  private final Provider<Context> contextProvider;

  private final Provider<OkHttpClient> okHttpClientProvider;

  public ExoPlayerManager_Factory(Provider<Context> contextProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    this.contextProvider = contextProvider;
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public ExoPlayerManager get() {
    return newInstance(contextProvider.get(), okHttpClientProvider.get());
  }

  public static ExoPlayerManager_Factory create(Provider<Context> contextProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    return new ExoPlayerManager_Factory(contextProvider, okHttpClientProvider);
  }

  public static ExoPlayerManager newInstance(Context context, OkHttpClient okHttpClient) {
    return new ExoPlayerManager(context, okHttpClient);
  }
}
