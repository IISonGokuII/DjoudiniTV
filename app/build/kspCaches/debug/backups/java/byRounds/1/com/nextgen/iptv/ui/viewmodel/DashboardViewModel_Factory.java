package com.nextgen.iptv.ui.viewmodel;

import com.nextgen.iptv.domain.repository.ProviderRepository;
import com.nextgen.iptv.domain.usecase.provider.GetProvidersWithStatusUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<ProviderRepository> providerRepositoryProvider;

  private final Provider<GetProvidersWithStatusUseCase> getProvidersWithStatusUseCaseProvider;

  public DashboardViewModel_Factory(Provider<ProviderRepository> providerRepositoryProvider,
      Provider<GetProvidersWithStatusUseCase> getProvidersWithStatusUseCaseProvider) {
    this.providerRepositoryProvider = providerRepositoryProvider;
    this.getProvidersWithStatusUseCaseProvider = getProvidersWithStatusUseCaseProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(providerRepositoryProvider.get(), getProvidersWithStatusUseCaseProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<ProviderRepository> providerRepositoryProvider,
      Provider<GetProvidersWithStatusUseCase> getProvidersWithStatusUseCaseProvider) {
    return new DashboardViewModel_Factory(providerRepositoryProvider, getProvidersWithStatusUseCaseProvider);
  }

  public static DashboardViewModel newInstance(ProviderRepository providerRepository,
      GetProvidersWithStatusUseCase getProvidersWithStatusUseCase) {
    return new DashboardViewModel(providerRepository, getProvidersWithStatusUseCase);
  }
}
