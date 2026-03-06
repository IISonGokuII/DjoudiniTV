package com.nextgen.iptv.domain.usecase.epg;

import com.nextgen.iptv.domain.repository.EpgRepository;
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
public final class GetCurrentEpgUseCase_Factory implements Factory<GetCurrentEpgUseCase> {
  private final Provider<EpgRepository> epgRepositoryProvider;

  public GetCurrentEpgUseCase_Factory(Provider<EpgRepository> epgRepositoryProvider) {
    this.epgRepositoryProvider = epgRepositoryProvider;
  }

  @Override
  public GetCurrentEpgUseCase get() {
    return newInstance(epgRepositoryProvider.get());
  }

  public static GetCurrentEpgUseCase_Factory create(Provider<EpgRepository> epgRepositoryProvider) {
    return new GetCurrentEpgUseCase_Factory(epgRepositoryProvider);
  }

  public static GetCurrentEpgUseCase newInstance(EpgRepository epgRepository) {
    return new GetCurrentEpgUseCase(epgRepository);
  }
}
