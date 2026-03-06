package com.nextgen.iptv.data.repository;

import com.nextgen.iptv.data.local.dao.VodProgressDao;
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
public final class VodProgressRepositoryImpl_Factory implements Factory<VodProgressRepositoryImpl> {
  private final Provider<VodProgressDao> vodProgressDaoProvider;

  public VodProgressRepositoryImpl_Factory(Provider<VodProgressDao> vodProgressDaoProvider) {
    this.vodProgressDaoProvider = vodProgressDaoProvider;
  }

  @Override
  public VodProgressRepositoryImpl get() {
    return newInstance(vodProgressDaoProvider.get());
  }

  public static VodProgressRepositoryImpl_Factory create(
      Provider<VodProgressDao> vodProgressDaoProvider) {
    return new VodProgressRepositoryImpl_Factory(vodProgressDaoProvider);
  }

  public static VodProgressRepositoryImpl newInstance(VodProgressDao vodProgressDao) {
    return new VodProgressRepositoryImpl(vodProgressDao);
  }
}
