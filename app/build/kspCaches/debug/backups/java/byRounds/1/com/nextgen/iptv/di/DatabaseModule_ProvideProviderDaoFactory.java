package com.nextgen.iptv.di;

import com.nextgen.iptv.data.local.AppDatabase;
import com.nextgen.iptv.data.local.dao.ProviderDao;
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
public final class DatabaseModule_ProvideProviderDaoFactory implements Factory<ProviderDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideProviderDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ProviderDao get() {
    return provideProviderDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideProviderDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideProviderDaoFactory(databaseProvider);
  }

  public static ProviderDao provideProviderDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideProviderDao(database));
  }
}
