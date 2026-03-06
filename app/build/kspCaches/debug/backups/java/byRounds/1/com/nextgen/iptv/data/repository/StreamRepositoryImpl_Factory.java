package com.nextgen.iptv.data.repository;

import com.nextgen.iptv.data.local.dao.StreamDao;
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
public final class StreamRepositoryImpl_Factory implements Factory<StreamRepositoryImpl> {
  private final Provider<StreamDao> streamDaoProvider;

  public StreamRepositoryImpl_Factory(Provider<StreamDao> streamDaoProvider) {
    this.streamDaoProvider = streamDaoProvider;
  }

  @Override
  public StreamRepositoryImpl get() {
    return newInstance(streamDaoProvider.get());
  }

  public static StreamRepositoryImpl_Factory create(Provider<StreamDao> streamDaoProvider) {
    return new StreamRepositoryImpl_Factory(streamDaoProvider);
  }

  public static StreamRepositoryImpl newInstance(StreamDao streamDao) {
    return new StreamRepositoryImpl(streamDao);
  }
}
