package com.nextgen.iptv.domain.usecase.progress;

import com.nextgen.iptv.domain.repository.VodProgressRepository;
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
public final class GetVodProgressUseCase_Factory implements Factory<GetVodProgressUseCase> {
  private final Provider<VodProgressRepository> vodProgressRepositoryProvider;

  public GetVodProgressUseCase_Factory(
      Provider<VodProgressRepository> vodProgressRepositoryProvider) {
    this.vodProgressRepositoryProvider = vodProgressRepositoryProvider;
  }

  @Override
  public GetVodProgressUseCase get() {
    return newInstance(vodProgressRepositoryProvider.get());
  }

  public static GetVodProgressUseCase_Factory create(
      Provider<VodProgressRepository> vodProgressRepositoryProvider) {
    return new GetVodProgressUseCase_Factory(vodProgressRepositoryProvider);
  }

  public static GetVodProgressUseCase newInstance(VodProgressRepository vodProgressRepository) {
    return new GetVodProgressUseCase(vodProgressRepository);
  }
}
