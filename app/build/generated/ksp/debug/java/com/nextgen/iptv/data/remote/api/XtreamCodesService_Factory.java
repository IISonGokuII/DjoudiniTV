package com.nextgen.iptv.data.remote.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.serialization.json.Json;
import okhttp3.OkHttpClient;

@ScopeMetadata
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
public final class XtreamCodesService_Factory implements Factory<XtreamCodesService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<Json> jsonProvider;

  public XtreamCodesService_Factory(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Json> jsonProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.jsonProvider = jsonProvider;
  }

  @Override
  public XtreamCodesService get() {
    return newInstance(okHttpClientProvider.get(), jsonProvider.get());
  }

  public static XtreamCodesService_Factory create(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Json> jsonProvider) {
    return new XtreamCodesService_Factory(okHttpClientProvider, jsonProvider);
  }

  public static XtreamCodesService newInstance(OkHttpClient okHttpClient, Json json) {
    return new XtreamCodesService(okHttpClient, json);
  }
}
