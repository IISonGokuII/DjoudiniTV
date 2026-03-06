package com.nextgen.iptv.ui.viewmodel;

import com.nextgen.iptv.domain.repository.CategoryRepository;
import com.nextgen.iptv.domain.repository.ProviderRepository;
import com.nextgen.iptv.domain.repository.SeriesRepository;
import com.nextgen.iptv.domain.repository.SettingsRepository;
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
public final class SeriesListViewModel_Factory implements Factory<SeriesListViewModel> {
  private final Provider<SeriesRepository> seriesRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<ProviderRepository> providerRepositoryProvider;

  public SeriesListViewModel_Factory(Provider<SeriesRepository> seriesRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ProviderRepository> providerRepositoryProvider) {
    this.seriesRepositoryProvider = seriesRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.providerRepositoryProvider = providerRepositoryProvider;
  }

  @Override
  public SeriesListViewModel get() {
    return newInstance(seriesRepositoryProvider.get(), categoryRepositoryProvider.get(), settingsRepositoryProvider.get(), providerRepositoryProvider.get());
  }

  public static SeriesListViewModel_Factory create(
      Provider<SeriesRepository> seriesRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ProviderRepository> providerRepositoryProvider) {
    return new SeriesListViewModel_Factory(seriesRepositoryProvider, categoryRepositoryProvider, settingsRepositoryProvider, providerRepositoryProvider);
  }

  public static SeriesListViewModel newInstance(SeriesRepository seriesRepository,
      CategoryRepository categoryRepository, SettingsRepository settingsRepository,
      ProviderRepository providerRepository) {
    return new SeriesListViewModel(seriesRepository, categoryRepository, settingsRepository, providerRepository);
  }
}
