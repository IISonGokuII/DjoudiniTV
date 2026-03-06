package com.nextgen.iptv.ui.viewmodel;

import androidx.lifecycle.SavedStateHandle;
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
public final class SeriesDetailViewModel_Factory implements Factory<SeriesDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<SeriesRepository> seriesRepositoryProvider;

  public SeriesDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<SeriesRepository> seriesRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.seriesRepositoryProvider = seriesRepositoryProvider;
  }

  @Override
  public SeriesDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), seriesRepositoryProvider.get());
  }

  public static SeriesDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<SeriesRepository> seriesRepositoryProvider) {
    return new SeriesDetailViewModel_Factory(savedStateHandleProvider, seriesRepositoryProvider);
  }

  public static SeriesDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      SeriesRepository seriesRepository) {
    return new SeriesDetailViewModel(savedStateHandle, seriesRepository);
  }
}
