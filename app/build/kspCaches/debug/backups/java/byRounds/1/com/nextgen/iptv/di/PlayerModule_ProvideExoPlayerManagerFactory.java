package com.nextgen.iptv.di;

import android.content.Context;
import com.nextgen.iptv.data.player.ExoPlayerManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("dagger.hilt.android.scopes.ViewModelScoped")
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
public final class PlayerModule_ProvideExoPlayerManagerFactory implements Factory<ExoPlayerManager> {
  private final Provider<Context> contextProvider;

  private final Provider<OkHttpClient> okHttpClientProvider;

  public PlayerModule_ProvideExoPlayerManagerFactory(Provider<Context> contextProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    this.contextProvider = contextProvider;
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public ExoPlayerManager get() {
    return provideExoPlayerManager(contextProvider.get(), okHttpClientProvider.get());
  }

  public static PlayerModule_ProvideExoPlayerManagerFactory create(
      Provider<Context> contextProvider, Provider<OkHttpClient> okHttpClientProvider) {
    return new PlayerModule_ProvideExoPlayerManagerFactory(contextProvider, okHttpClientProvider);
  }

  public static ExoPlayerManager provideExoPlayerManager(Context context,
      OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(PlayerModule.INSTANCE.provideExoPlayerManager(context, okHttpClient));
  }
}
