package com.nextgen.iptv.domain.usecase.stream;

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
public final class GetStreamsByCategoryUseCase_Factory implements Factory<GetStreamsByCategoryUseCase> {
  private final Provider<StreamRepository> streamRepositoryProvider;

  public GetStreamsByCategoryUseCase_Factory(Provider<StreamRepository> streamRepositoryProvider) {
    this.streamRepositoryProvider = streamRepositoryProvider;
  }

  @Override
  public GetStreamsByCategoryUseCase get() {
    return newInstance(streamRepositoryProvider.get());
  }

  public static GetStreamsByCategoryUseCase_Factory create(
      Provider<StreamRepository> streamRepositoryProvider) {
    return new GetStreamsByCategoryUseCase_Factory(streamRepositoryProvider);
  }

  public static GetStreamsByCategoryUseCase newInstance(StreamRepository streamRepository) {
    return new GetStreamsByCategoryUseCase(streamRepository);
  }
}
