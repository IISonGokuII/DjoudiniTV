package com.nextgen.iptv.domain.usecase.favorite;

import com.nextgen.iptv.domain.repository.FavoriteRepository;
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
public final class ToggleFavoriteUseCase_Factory implements Factory<ToggleFavoriteUseCase> {
  private final Provider<FavoriteRepository> favoriteRepositoryProvider;

  public ToggleFavoriteUseCase_Factory(Provider<FavoriteRepository> favoriteRepositoryProvider) {
    this.favoriteRepositoryProvider = favoriteRepositoryProvider;
  }

  @Override
  public ToggleFavoriteUseCase get() {
    return newInstance(favoriteRepositoryProvider.get());
  }

  public static ToggleFavoriteUseCase_Factory create(
      Provider<FavoriteRepository> favoriteRepositoryProvider) {
    return new ToggleFavoriteUseCase_Factory(favoriteRepositoryProvider);
  }

  public static ToggleFavoriteUseCase newInstance(FavoriteRepository favoriteRepository) {
    return new ToggleFavoriteUseCase(favoriteRepository);
  }
}
