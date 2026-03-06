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
public final class DeleteProviderUseCase_Factory implements Factory<DeleteProviderUseCase> {
  private final Provider<ProviderRepository> providerRepositoryProvider;

  public DeleteProviderUseCase_Factory(Provider<ProviderRepository> providerRepositoryProvider) {
    this.providerRepositoryProvider = providerRepositoryProvider;
  }

  @Override
  public DeleteProviderUseCase get() {
    return newInstance(providerRepositoryProvider.get());
  }

  public static DeleteProviderUseCase_Factory create(
      Provider<ProviderRepository> providerRepositoryProvider) {
    return new DeleteProviderUseCase_Factory(providerRepositoryProvider);
  }

  public static DeleteProviderUseCase newInstance(ProviderRepository providerRepository) {
    return new DeleteProviderUseCase(providerRepository);
  }
}
