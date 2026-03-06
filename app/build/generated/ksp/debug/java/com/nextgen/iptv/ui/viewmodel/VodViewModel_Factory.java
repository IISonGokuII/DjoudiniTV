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
public final class VodViewModel_Factory implements Factory<VodViewModel> {
  private final Provider<StreamRepository> streamRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<ProviderRepository> providerRepositoryProvider;

  public VodViewModel_Factory(Provider<StreamRepository> streamRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ProviderRepository> providerRepositoryProvider) {
    this.streamRepositoryProvider = streamRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.providerRepositoryProvider = providerRepositoryProvider;
  }

  @Override
  public VodViewModel get() {
    return newInstance(streamRepositoryProvider.get(), categoryRepositoryProvider.get(), settingsRepositoryProvider.get(), providerRepositoryProvider.get());
  }

  public static VodViewModel_Factory create(Provider<StreamRepository> streamRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ProviderRepository> providerRepositoryProvider) {
    return new VodViewModel_Factory(streamRepositoryProvider, categoryRepositoryProvider, settingsRepositoryProvider, providerRepositoryProvider);
  }

  public static VodViewModel newInstance(StreamRepository streamRepository,
      CategoryRepository categoryRepository, SettingsRepository settingsRepository,
      ProviderRepository providerRepository) {
    return new VodViewModel(streamRepository, categoryRepository, settingsRepository, providerRepository);
  }
}
