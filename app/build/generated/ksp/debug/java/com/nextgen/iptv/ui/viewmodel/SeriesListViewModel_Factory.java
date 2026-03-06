package com.nextgen.iptv.ui.viewmodel;

import com.nextgen.iptv.domain.repository.CategoryRepository;
import com.nextgen.iptv.domain.repository.SeriesRepository;
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
public final class SeriesListViewModel_Factory implements Factory<SeriesListViewModel> {
  private final Provider<SeriesRepository> seriesRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public SeriesListViewModel_Factory(Provider<SeriesRepository> seriesRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    this.seriesRepositoryProvider = seriesRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public SeriesListViewModel get() {
    return newInstance(seriesRepositoryProvider.get(), categoryRepositoryProvider.get());
  }

  public static SeriesListViewModel_Factory create(
      Provider<SeriesRepository> seriesRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new SeriesListViewModel_Factory(seriesRepositoryProvider, categoryRepositoryProvider);
  }

  public static SeriesListViewModel newInstance(SeriesRepository seriesRepository,
      CategoryRepository categoryRepository) {
    return new SeriesListViewModel(seriesRepository, categoryRepository);
  }
}
