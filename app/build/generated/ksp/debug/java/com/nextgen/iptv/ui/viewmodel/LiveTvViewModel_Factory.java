package com.nextgen.iptv.ui.viewmodel;

import com.nextgen.iptv.domain.repository.CategoryRepository;
import com.nextgen.iptv.domain.repository.ProviderRepository;
import com.nextgen.iptv.domain.repository.SettingsRepository;
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
public final class LiveTvViewModel_Factory implements Factory<LiveTvViewModel> {
  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<StreamRepository> streamRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<ProviderRepository> providerRepositoryProvider;

  public LiveTvViewModel_Factory(Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<StreamRepository> streamRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ProviderRepository> providerRepositoryProvider) {
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.streamRepositoryProvider = streamRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.providerRepositoryProvider = providerRepositoryProvider;
  }

  @Override
  public LiveTvViewModel get() {
    return newInstance(categoryRepositoryProvider.get(), streamRepositoryProvider.get(), settingsRepositoryProvider.get(), providerRepositoryProvider.get());
  }

  public static LiveTvViewModel_Factory create(
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<StreamRepository> streamRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ProviderRepository> providerRepositoryProvider) {
    return new LiveTvViewModel_Factory(categoryRepositoryProvider, streamRepositoryProvider, settingsRepositoryProvider, providerRepositoryProvider);
  }

  public static LiveTvViewModel newInstance(CategoryRepository categoryRepository,
      StreamRepository streamRepository, SettingsRepository settingsRepository,
      ProviderRepository providerRepository) {
    return new LiveTvViewModel(categoryRepository, streamRepository, settingsRepository, providerRepository);
  }
}
