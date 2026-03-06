package com.nextgen.iptv.ui.viewmodel;

import com.nextgen.iptv.domain.repository.CategoryRepository;
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
public final class LiveTvViewModel_Factory implements Factory<LiveTvViewModel> {
  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<StreamRepository> streamRepositoryProvider;

  public LiveTvViewModel_Factory(Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<StreamRepository> streamRepositoryProvider) {
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.streamRepositoryProvider = streamRepositoryProvider;
  }

  @Override
  public LiveTvViewModel get() {
    return newInstance(categoryRepositoryProvider.get(), streamRepositoryProvider.get());
  }

  public static LiveTvViewModel_Factory create(
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<StreamRepository> streamRepositoryProvider) {
    return new LiveTvViewModel_Factory(categoryRepositoryProvider, streamRepositoryProvider);
  }

  public static LiveTvViewModel newInstance(CategoryRepository categoryRepository,
      StreamRepository streamRepository) {
    return new LiveTvViewModel(categoryRepository, streamRepository);
  }
}
