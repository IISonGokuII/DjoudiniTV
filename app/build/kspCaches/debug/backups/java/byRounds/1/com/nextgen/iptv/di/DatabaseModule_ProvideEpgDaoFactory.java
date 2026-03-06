package com.nextgen.iptv.di;

import com.nextgen.iptv.data.local.AppDatabase;
import com.nextgen.iptv.data.local.dao.EpgEventDao;
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
public final class DatabaseModule_ProvideEpgDaoFactory implements Factory<EpgEventDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideEpgDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public EpgEventDao get() {
    return provideEpgDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideEpgDaoFactory create(Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideEpgDaoFactory(databaseProvider);
  }

  public static EpgEventDao provideEpgDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideEpgDao(database));
  }
}
