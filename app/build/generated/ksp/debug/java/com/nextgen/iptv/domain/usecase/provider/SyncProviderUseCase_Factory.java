package com.nextgen.iptv.domain.usecase.provider;

import com.nextgen.iptv.data.remote.api.XtreamCodesService;
import com.nextgen.iptv.domain.repository.CategoryRepository;
import com.nextgen.iptv.domain.repository.ProviderRepository;
import com.nextgen.iptv.domain.repository.SeriesRepository;
import com.nextgen.iptv.domain.repository.StreamRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SyncProviderUseCase_Factory implements Factory<SyncProviderUseCase> {
  private final Provider<ProviderRepository> providerRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<StreamRepository> streamRepositoryProvider;

  private final Provider<SeriesRepository> seriesRepositoryProvider;

  private final Provider<XtreamCodesService> xtreamCodesServiceProvider;

  public SyncProviderUseCase_Factory(Provider<ProviderRepository> providerRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<StreamRepository> streamRepositoryProvider,
      Provider<SeriesRepository> seriesRepositoryProvider,
      Provider<XtreamCodesService> xtreamCodesServiceProvider) {
    this.providerRepositoryProvider = providerRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.streamRepositoryProvider = streamRepositoryProvider;
    this.seriesRepositoryProvider = seriesRepositoryProvider;
    this.xtreamCodesServiceProvider = xtreamCodesServiceProvider;
  }

  @Override
  public SyncProviderUseCase get() {
    return newInstance(providerRepositoryProvider.get(), categoryRepositoryProvider.get(), streamRepositoryProvider.get(), seriesRepositoryProvider.get(), xtreamCodesServiceProvider.get());
  }

  public static SyncProviderUseCase_Factory create(
      Provider<ProviderRepository> providerRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<StreamRepository> streamRepositoryProvider,
      Provider<SeriesRepository> seriesRepositoryProvider,
      Provider<XtreamCodesService> xtreamCodesServiceProvider) {
    return new SyncProviderUseCase_Factory(providerRepositoryProvider, categoryRepositoryProvider, streamRepositoryProvider, seriesRepositoryProvider, xtreamCodesServiceProvider);
  }

  public static SyncProviderUseCase newInstance(ProviderRepository providerRepository,
      CategoryRepository categoryRepository, StreamRepository streamRepository,
      SeriesRepository seriesRepository, XtreamCodesService xtreamCodesService) {
    return new SyncProviderUseCase(providerRepository, categoryRepository, streamRepository, seriesRepository, xtreamCodesService);
  }
}
