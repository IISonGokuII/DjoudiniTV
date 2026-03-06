package com.nextgen.iptv.domain.usecase.provider;

import com.nextgen.iptv.data.remote.api.XtreamCodesService;
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
public final class ValidateProviderUseCase_Factory implements Factory<ValidateProviderUseCase> {
  private final Provider<XtreamCodesService> xtreamCodesServiceProvider;

  public ValidateProviderUseCase_Factory(Provider<XtreamCodesService> xtreamCodesServiceProvider) {
    this.xtreamCodesServiceProvider = xtreamCodesServiceProvider;
  }

  @Override
  public ValidateProviderUseCase get() {
    return newInstance(xtreamCodesServiceProvider.get());
  }

  public static ValidateProviderUseCase_Factory create(
      Provider<XtreamCodesService> xtreamCodesServiceProvider) {
    return new ValidateProviderUseCase_Factory(xtreamCodesServiceProvider);
  }

  public static ValidateProviderUseCase newInstance(XtreamCodesService xtreamCodesService) {
    return new ValidateProviderUseCase(xtreamCodesService);
  }
}
