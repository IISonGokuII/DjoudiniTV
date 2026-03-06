package com.nextgen.iptv.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class EpgSyncWorker_AssistedFactory_Impl implements EpgSyncWorker_AssistedFactory {
  private final EpgSyncWorker_Factory delegateFactory;

  EpgSyncWorker_AssistedFactory_Impl(EpgSyncWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public EpgSyncWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<EpgSyncWorker_AssistedFactory> create(
      EpgSyncWorker_Factory delegateFactory) {
    return InstanceFactory.create(new EpgSyncWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<EpgSyncWorker_AssistedFactory> createFactoryProvider(
      EpgSyncWorker_Factory delegateFactory) {
    return InstanceFactory.create(new EpgSyncWorker_AssistedFactory_Impl(delegateFactory));
  }
}
