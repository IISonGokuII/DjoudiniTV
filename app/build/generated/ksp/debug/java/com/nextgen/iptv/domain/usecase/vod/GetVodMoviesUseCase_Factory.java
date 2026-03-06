package com.nextgen.iptv.domain.usecase.vod;

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
public final class GetVodMoviesUseCase_Factory implements Factory<GetVodMoviesUseCase> {
  private final Provider<StreamRepository> streamRepositoryProvider;

  public GetVodMoviesUseCase_Factory(Provider<StreamRepository> streamRepositoryProvider) {
    this.streamRepositoryProvider = streamRepositoryProvider;
  }

  @Override
  public GetVodMoviesUseCase get() {
    return newInstance(streamRepositoryProvider.get());
  }

  public static GetVodMoviesUseCase_Factory create(
      Provider<StreamRepository> streamRepositoryProvider) {
    return new GetVodMoviesUseCase_Factory(streamRepositoryProvider);
  }

  public static GetVodMoviesUseCase newInstance(StreamRepository streamRepository) {
    return new GetVodMoviesUseCase(streamRepository);
  }
}
