package com.nextgen.iptv.domain.usecase.provider;

import com.nextgen.iptv.domain.repository.ProviderRepository;
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
public final class GetProvidersUseCase_Factory implements Factory<GetProvidersUseCase> {
  private final Provider<ProviderRepository> providerRepositoryProvider;

  public GetProvidersUseCase_Factory(Provider<ProviderRepository> providerRepositoryProvider) {
    this.providerRepositoryProvider = providerRepositoryProvider;
  }

  @Override
  public GetProvidersUseCase get() {
    return newInstance(providerRepositoryProvider.get());
  }

  public static GetProvidersUseCase_Factory create(
      Provider<ProviderRepository> providerRepositoryProvider) {
    return new GetProvidersUseCase_Factory(providerRepositoryProvider);
  }

  public static GetProvidersUseCase newInstance(ProviderRepository providerRepository) {
    return new GetProvidersUseCase(providerRepository);
  }
}
