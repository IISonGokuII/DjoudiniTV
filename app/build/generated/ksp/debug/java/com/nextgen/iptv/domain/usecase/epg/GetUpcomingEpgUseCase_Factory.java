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
public final class GetUpcomingEpgUseCase_Factory implements Factory<GetUpcomingEpgUseCase> {
  private final Provider<EpgRepository> epgRepositoryProvider;

  public GetUpcomingEpgUseCase_Factory(Provider<EpgRepository> epgRepositoryProvider) {
    this.epgRepositoryProvider = epgRepositoryProvider;
  }

  @Override
  public GetUpcomingEpgUseCase get() {
    return newInstance(epgRepositoryProvider.get());
  }

  public static GetUpcomingEpgUseCase_Factory create(
      Provider<EpgRepository> epgRepositoryProvider) {
    return new GetUpcomingEpgUseCase_Factory(epgRepositoryProvider);
  }

  public static GetUpcomingEpgUseCase newInstance(EpgRepository epgRepository) {
    return new GetUpcomingEpgUseCase(epgRepository);
  }
}
