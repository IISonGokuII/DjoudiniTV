package com.nextgen.iptv.domain.usecase.provider;

import com.nextgen.iptv.domain.repository.ProviderRepository;
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
public final class GetProvidersWithStatusUseCase_Factory implements Factory<GetProvidersWithStatusUseCase> {
  private final Provider<ProviderRepository> providerRepositoryProvider;

  private final Provider<StreamRepository> streamRepositoryProvider;

  public GetProvidersWithStatusUseCase_Factory(
      Provider<ProviderRepository> providerRepositoryProvider,
      Provider<StreamRepository> streamRepositoryProvider) {
    this.providerRepositoryProvider = providerRepositoryProvider;
    this.streamRepositoryProvider = streamRepositoryProvider;
  }

  @Override
  public GetProvidersWithStatusUseCase get() {
    return newInstance(providerRepositoryProvider.get(), streamRepositoryProvider.get());
  }

  public static GetProvidersWithStatusUseCase_Factory create(
      Provider<ProviderRepository> providerRepositoryProvider,
      Provider<StreamRepository> streamRepositoryProvider) {
    return new GetProvidersWithStatusUseCase_Factory(providerRepositoryProvider, streamRepositoryProvider);
  }

  public static GetProvidersWithStatusUseCase newInstance(ProviderRepository providerRepository,
      StreamRepository streamRepository) {
    return new GetProvidersWithStatusUseCase(providerRepository, streamRepository);
  }
}
