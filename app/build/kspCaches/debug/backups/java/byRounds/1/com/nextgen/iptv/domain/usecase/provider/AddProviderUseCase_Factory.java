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
public final class AddProviderUseCase_Factory implements Factory<AddProviderUseCase> {
  private final Provider<ProviderRepository> providerRepositoryProvider;

  public AddProviderUseCase_Factory(Provider<ProviderRepository> providerRepositoryProvider) {
    this.providerRepositoryProvider = providerRepositoryProvider;
  }

  @Override
  public AddProviderUseCase get() {
    return newInstance(providerRepositoryProvider.get());
  }

  public static AddProviderUseCase_Factory create(
      Provider<ProviderRepository> providerRepositoryProvider) {
    return new AddProviderUseCase_Factory(providerRepositoryProvider);
  }

  public static AddProviderUseCase newInstance(ProviderRepository providerRepository) {
    return new AddProviderUseCase(providerRepository);
  }
}
