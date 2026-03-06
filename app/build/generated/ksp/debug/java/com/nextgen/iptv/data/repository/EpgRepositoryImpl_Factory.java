package com.nextgen.iptv.data.repository;

import com.nextgen.iptv.data.local.dao.EpgEventDao;
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
public final class EpgRepositoryImpl_Factory implements Factory<EpgRepositoryImpl> {
  private final Provider<EpgEventDao> epgDaoProvider;

  public EpgRepositoryImpl_Factory(Provider<EpgEventDao> epgDaoProvider) {
    this.epgDaoProvider = epgDaoProvider;
  }

  @Override
  public EpgRepositoryImpl get() {
    return newInstance(epgDaoProvider.get());
  }

  public static EpgRepositoryImpl_Factory create(Provider<EpgEventDao> epgDaoProvider) {
    return new EpgRepositoryImpl_Factory(epgDaoProvider);
  }

  public static EpgRepositoryImpl newInstance(EpgEventDao epgDao) {
    return new EpgRepositoryImpl(epgDao);
  }
}
