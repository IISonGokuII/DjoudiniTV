package com.nextgen.iptv.presentation.onboarding;

import com.nextgen.iptv.domain.repository.CategoryRepository;
import com.nextgen.iptv.domain.usecase.provider.AddProviderUseCase;
import com.nextgen.iptv.domain.usecase.provider.SyncProviderUseCase;
import com.nextgen.iptv.domain.usecase.provider.ValidateProviderUseCase;
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
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<AddProviderUseCase> addProviderUseCaseProvider;

  private final Provider<SyncProviderUseCase> syncProviderUseCaseProvider;

  private final Provider<ValidateProviderUseCase> validateProviderUseCaseProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public OnboardingViewModel_Factory(Provider<AddProviderUseCase> addProviderUseCaseProvider,
      Provider<SyncProviderUseCase> syncProviderUseCaseProvider,
      Provider<ValidateProviderUseCase> validateProviderUseCaseProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    this.addProviderUseCaseProvider = addProviderUseCaseProvider;
    this.syncProviderUseCaseProvider = syncProviderUseCaseProvider;
    this.validateProviderUseCaseProvider = validateProviderUseCaseProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(addProviderUseCaseProvider.get(), syncProviderUseCaseProvider.get(), validateProviderUseCaseProvider.get(), categoryRepositoryProvider.get());
  }

  public static OnboardingViewModel_Factory create(
      Provider<AddProviderUseCase> addProviderUseCaseProvider,
      Provider<SyncProviderUseCase> syncProviderUseCaseProvider,
      Provider<ValidateProviderUseCase> validateProviderUseCaseProvider,
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new OnboardingViewModel_Factory(addProviderUseCaseProvider, syncProviderUseCaseProvider, validateProviderUseCaseProvider, categoryRepositoryProvider);
  }

  public static OnboardingViewModel newInstance(AddProviderUseCase addProviderUseCase,
      SyncProviderUseCase syncProviderUseCase, ValidateProviderUseCase validateProviderUseCase,
      CategoryRepository categoryRepository) {
    return new OnboardingViewModel(addProviderUseCase, syncProviderUseCase, validateProviderUseCase, categoryRepository);
  }
}
