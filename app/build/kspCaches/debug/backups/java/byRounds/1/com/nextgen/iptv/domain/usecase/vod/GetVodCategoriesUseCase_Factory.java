package com.nextgen.iptv.domain.usecase.vod;

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
public final class GetVodCategoriesUseCase_Factory implements Factory<GetVodCategoriesUseCase> {
  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public GetVodCategoriesUseCase_Factory(Provider<CategoryRepository> categoryRepositoryProvider) {
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public GetVodCategoriesUseCase get() {
    return newInstance(categoryRepositoryProvider.get());
  }

  public static GetVodCategoriesUseCase_Factory create(
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new GetVodCategoriesUseCase_Factory(categoryRepositoryProvider);
  }

  public static GetVodCategoriesUseCase newInstance(CategoryRepository categoryRepository) {
    return new GetVodCategoriesUseCase(categoryRepository);
  }
}
