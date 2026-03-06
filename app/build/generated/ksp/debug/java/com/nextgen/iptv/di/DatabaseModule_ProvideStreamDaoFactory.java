package com.nextgen.iptv.di;

import com.nextgen.iptv.data.local.AppDatabase;
import com.nextgen.iptv.data.local.dao.StreamDao;
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
public final class DatabaseModule_ProvideStreamDaoFactory implements Factory<StreamDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideStreamDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public StreamDao get() {
    return provideStreamDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideStreamDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideStreamDaoFactory(databaseProvider);
  }

  public static StreamDao provideStreamDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStreamDao(database));
  }
}
