package com.nextgen.iptv.domain.usecase.stream;

import com.nextgen.iptv.domain.repository.CategoryRepository;
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
public final class GetLiveCategoriesUseCase_Factory implements Factory<GetLiveCategoriesUseCase> {
  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public GetLiveCategoriesUseCase_Factory(Provider<CategoryRepository> categoryRepositoryProvider) {
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public GetLiveCategoriesUseCase get() {
    return newInstance(categoryRepositoryProvider.get());
  }

  public static GetLiveCategoriesUseCase_Factory create(
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new GetLiveCategoriesUseCase_Factory(categoryRepositoryProvider);
  }

  public static GetLiveCategoriesUseCase newInstance(CategoryRepository categoryRepository) {
    return new GetLiveCategoriesUseCase(categoryRepository);
  }
}
