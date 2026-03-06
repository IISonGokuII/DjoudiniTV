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
public final class SaveVodProgressUseCase_Factory implements Factory<SaveVodProgressUseCase> {
  private final Provider<VodProgressRepository> vodProgressRepositoryProvider;

  public SaveVodProgressUseCase_Factory(
      Provider<VodProgressRepository> vodProgressRepositoryProvider) {
    this.vodProgressRepositoryProvider = vodProgressRepositoryProvider;
  }

  @Override
  public SaveVodProgressUseCase get() {
    return newInstance(vodProgressRepositoryProvider.get());
  }

  public static SaveVodProgressUseCase_Factory create(
      Provider<VodProgressRepository> vodProgressRepositoryProvider) {
    return new SaveVodProgressUseCase_Factory(vodProgressRepositoryProvider);
  }

  public static SaveVodProgressUseCase newInstance(VodProgressRepository vodProgressRepository) {
    return new SaveVodProgressUseCase(vodProgressRepository);
  }
}
