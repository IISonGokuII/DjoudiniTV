package com.nextgen.iptv.di;

import com.nextgen.iptv.data.local.AppDatabase;
import com.nextgen.iptv.data.local.dao.SeriesDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideSeriesDaoFactory implements Factory<SeriesDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideSeriesDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SeriesDao get() {
    return provideSeriesDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideSeriesDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideSeriesDaoFactory(databaseProvider);
  }

  public static SeriesDao provideSeriesDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSeriesDao(database));
  }
}
