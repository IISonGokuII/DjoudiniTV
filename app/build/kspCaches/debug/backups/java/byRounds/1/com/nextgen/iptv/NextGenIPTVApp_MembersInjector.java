package com.nextgen.iptv;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class NextGenIPTVApp_MembersInjector implements MembersInjector<NextGenIPTVApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public NextGenIPTVApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<NextGenIPTVApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new NextGenIPTVApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(NextGenIPTVApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.nextgen.iptv.NextGenIPTVApp.workerFactory")
  public static void injectWorkerFactory(NextGenIPTVApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
