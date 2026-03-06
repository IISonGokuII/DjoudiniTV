package com.nextgen.iptv.di;

import com.nextgen.iptv.data.remote.api.XtreamCodesService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.serialization.json.Json;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class NetworkModule_ProvideXtreamCodesServiceFactory implements Factory<XtreamCodesService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<Json> jsonProvider;

  public NetworkModule_ProvideXtreamCodesServiceFactory(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Json> jsonProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.jsonProvider = jsonProvider;
  }

  @Override
  public XtreamCodesService get() {
    return provideXtreamCodesService(okHttpClientProvider.get(), jsonProvider.get());
  }

  public static NetworkModule_ProvideXtreamCodesServiceFactory create(
      Provider<OkHttpClient> okHttpClientProvider, Provider<Json> jsonProvider) {
    return new NetworkModule_ProvideXtreamCodesServiceFactory(okHttpClientProvider, jsonProvider);
  }

  public static XtreamCodesService provideXtreamCodesService(OkHttpClient okHttpClient, Json json) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideXtreamCodesService(okHttpClient, json));
  }
}
