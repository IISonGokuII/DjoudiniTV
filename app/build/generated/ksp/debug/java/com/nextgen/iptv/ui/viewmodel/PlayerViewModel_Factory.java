package com.nextgen.iptv.ui.viewmodel;

import androidx.lifecycle.SavedStateHandle;
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
public final class PlayerViewModel_Factory implements Factory<PlayerViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public PlayerViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public PlayerViewModel get() {
    return newInstance(savedStateHandleProvider.get());
  }

  public static PlayerViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new PlayerViewModel_Factory(savedStateHandleProvider);
  }

  public static PlayerViewModel newInstance(SavedStateHandle savedStateHandle) {
    return new PlayerViewModel(savedStateHandle);
  }
}
