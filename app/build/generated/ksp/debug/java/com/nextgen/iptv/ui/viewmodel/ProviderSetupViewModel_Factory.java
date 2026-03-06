package com.nextgen.iptv.ui.viewmodel;

import com.nextgen.iptv.domain.usecase.provider.AddProviderUseCase;
import com.nextgen.iptv.domain.usecase.provider.SyncProviderUseCase;
import com.nextgen.iptv.domain.usecase.provider.ValidateProviderUseCase;
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
public final class ProviderSetupViewModel_Factory implements Factory<ProviderSetupViewModel> {
  private final Provider<AddProviderUseCase> addProviderUseCaseProvider;

  private final Provider<ValidateProviderUseCase> validateProviderUseCaseProvider;

  private final Provider<SyncProviderUseCase> syncProviderUseCaseProvider;

  public ProviderSetupViewModel_Factory(Provider<AddProviderUseCase> addProviderUseCaseProvider,
      Provider<ValidateProviderUseCase> validateProviderUseCaseProvider,
      Provider<SyncProviderUseCase> syncProviderUseCaseProvider) {
    this.addProviderUseCaseProvider = addProviderUseCaseProvider;
    this.validateProviderUseCaseProvider = validateProviderUseCaseProvider;
    this.syncProviderUseCaseProvider = syncProviderUseCaseProvider;
  }

  @Override
  public ProviderSetupViewModel get() {
    return newInstance(addProviderUseCaseProvider.get(), validateProviderUseCaseProvider.get(), syncProviderUseCaseProvider.get());
  }

  public static ProviderSetupViewModel_Factory create(
      Provider<AddProviderUseCase> addProviderUseCaseProvider,
      Provider<ValidateProviderUseCase> validateProviderUseCaseProvider,
      Provider<SyncProviderUseCase> syncProviderUseCaseProvider) {
    return new ProviderSetupViewModel_Factory(addProviderUseCaseProvider, validateProviderUseCaseProvider, syncProviderUseCaseProvider);
  }

  public static ProviderSetupViewModel newInstance(AddProviderUseCase addProviderUseCase,
      ValidateProviderUseCase validateProviderUseCase, SyncProviderUseCase syncProviderUseCase) {
    return new ProviderSetupViewModel(addProviderUseCase, validateProviderUseCase, syncProviderUseCase);
  }
}
