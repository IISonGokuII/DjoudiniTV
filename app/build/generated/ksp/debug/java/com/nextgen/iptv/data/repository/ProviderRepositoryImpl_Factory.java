package com.nextgen.iptv.data.repository;

import com.nextgen.iptv.data.local.dao.ProviderDao;
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
public final class ProviderRepositoryImpl_Factory implements Factory<ProviderRepositoryImpl> {
  private final Provider<ProviderDao> providerDaoProvider;

  public ProviderRepositoryImpl_Factory(Provider<ProviderDao> providerDaoProvider) {
    this.providerDaoProvider = providerDaoProvider;
  }

  @Override
  public ProviderRepositoryImpl get() {
    return newInstance(providerDaoProvider.get());
  }

  public static ProviderRepositoryImpl_Factory create(Provider<ProviderDao> providerDaoProvider) {
    return new ProviderRepositoryImpl_Factory(providerDaoProvider);
  }

  public static ProviderRepositoryImpl newInstance(ProviderDao providerDao) {
    return new ProviderRepositoryImpl(providerDao);
  }
}
