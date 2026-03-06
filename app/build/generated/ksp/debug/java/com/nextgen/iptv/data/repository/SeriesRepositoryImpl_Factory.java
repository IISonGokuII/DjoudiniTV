package com.nextgen.iptv.data.repository;

import com.nextgen.iptv.data.local.dao.SeriesDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SeriesRepositoryImpl_Factory implements Factory<SeriesRepositoryImpl> {
  private final Provider<SeriesDao> seriesDaoProvider;

  public SeriesRepositoryImpl_Factory(Provider<SeriesDao> seriesDaoProvider) {
    this.seriesDaoProvider = seriesDaoProvider;
  }

  @Override
  public SeriesRepositoryImpl get() {
    return newInstance(seriesDaoProvider.get());
  }

  public static SeriesRepositoryImpl_Factory create(Provider<SeriesDao> seriesDaoProvider) {
    return new SeriesRepositoryImpl_Factory(seriesDaoProvider);
  }

  public static SeriesRepositoryImpl newInstance(SeriesDao seriesDao) {
    return new SeriesRepositoryImpl(seriesDao);
  }
}
