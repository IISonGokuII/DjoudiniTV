package com.nextgen.iptv.ui.viewmodel;

import com.nextgen.iptv.domain.repository.ProviderRepository;
import com.nextgen.iptv.domain.repository.SettingsRepository;
import com.nextgen.iptv.domain.usecase.provider.SyncProviderUseCase;
import com.nextgen.iptv.domain.usecase.settings.GetSettingsUseCase;
import com.nextgen.iptv.domain.usecase.settings.UpdateSettingsUseCase;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<GetSettingsUseCase> getSettingsUseCaseProvider;

  private final Provider<UpdateSettingsUseCase> updateSettingsUseCaseProvider;

  private final Provider<ProviderRepository> providerRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<SyncProviderUseCase> syncProviderUseCaseProvider;

  public SettingsViewModel_Factory(Provider<GetSettingsUseCase> getSettingsUseCaseProvider,
      Provider<UpdateSettingsUseCase> updateSettingsUseCaseProvider,
      Provider<ProviderRepository> providerRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SyncProviderUseCase> syncProviderUseCaseProvider) {
    this.getSettingsUseCaseProvider = getSettingsUseCaseProvider;
    this.updateSettingsUseCaseProvider = updateSettingsUseCaseProvider;
    this.providerRepositoryProvider = providerRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.syncProviderUseCaseProvider = syncProviderUseCaseProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(getSettingsUseCaseProvider.get(), updateSettingsUseCaseProvider.get(), providerRepositoryProvider.get(), settingsRepositoryProvider.get(), syncProviderUseCaseProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<GetSettingsUseCase> getSettingsUseCaseProvider,
      Provider<UpdateSettingsUseCase> updateSettingsUseCaseProvider,
      Provider<ProviderRepository> providerRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SyncProviderUseCase> syncProviderUseCaseProvider) {
    return new SettingsViewModel_Factory(getSettingsUseCaseProvider, updateSettingsUseCaseProvider, providerRepositoryProvider, settingsRepositoryProvider, syncProviderUseCaseProvider);
  }

  public static SettingsViewModel newInstance(GetSettingsUseCase getSettingsUseCase,
      UpdateSettingsUseCase updateSettingsUseCase, ProviderRepository providerRepository,
      SettingsRepository settingsRepository, SyncProviderUseCase syncProviderUseCase) {
    return new SettingsViewModel(getSettingsUseCase, updateSettingsUseCase, providerRepository, settingsRepository, syncProviderUseCase);
  }
}
