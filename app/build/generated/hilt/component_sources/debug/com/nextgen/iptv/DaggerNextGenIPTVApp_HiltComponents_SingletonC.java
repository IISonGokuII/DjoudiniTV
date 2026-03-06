package com.nextgen.iptv;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.nextgen.iptv.data.local.AppDatabase;
import com.nextgen.iptv.data.local.dao.CategoryDao;
import com.nextgen.iptv.data.local.dao.EpgEventDao;
import com.nextgen.iptv.data.local.dao.ProviderDao;
import com.nextgen.iptv.data.local.dao.SeriesDao;
import com.nextgen.iptv.data.local.dao.StreamDao;
import com.nextgen.iptv.data.parser.XmlTvParser;
import com.nextgen.iptv.data.remote.api.XtreamCodesService;
import com.nextgen.iptv.data.repository.CategoryRepositoryImpl;
import com.nextgen.iptv.data.repository.EpgRepositoryImpl;
import com.nextgen.iptv.data.repository.ProviderRepositoryImpl;
import com.nextgen.iptv.data.repository.SeriesRepositoryImpl;
import com.nextgen.iptv.data.repository.SettingsRepositoryImpl;
import com.nextgen.iptv.data.repository.StreamRepositoryImpl;
import com.nextgen.iptv.di.DatabaseModule_ProvideAppDatabaseFactory;
import com.nextgen.iptv.di.DatabaseModule_ProvideCategoryDaoFactory;
import com.nextgen.iptv.di.DatabaseModule_ProvideEpgDaoFactory;
import com.nextgen.iptv.di.DatabaseModule_ProvideProviderDaoFactory;
import com.nextgen.iptv.di.DatabaseModule_ProvideSeriesDaoFactory;
import com.nextgen.iptv.di.DatabaseModule_ProvideStreamDaoFactory;
import com.nextgen.iptv.di.NetworkModule_ProvideJsonFactory;
import com.nextgen.iptv.di.NetworkModule_ProvideOkHttpClientFactory;
import com.nextgen.iptv.di.NetworkModule_ProvideXtreamCodesServiceFactory;
import com.nextgen.iptv.domain.usecase.provider.AddProviderUseCase;
import com.nextgen.iptv.domain.usecase.provider.GetProvidersWithStatusUseCase;
import com.nextgen.iptv.domain.usecase.provider.SyncProviderUseCase;
import com.nextgen.iptv.domain.usecase.provider.ValidateProviderUseCase;
import com.nextgen.iptv.domain.usecase.settings.GetSettingsUseCase;
import com.nextgen.iptv.domain.usecase.settings.UpdateSettingsUseCase;
import com.nextgen.iptv.presentation.onboarding.OnboardingViewModel;
import com.nextgen.iptv.presentation.onboarding.OnboardingViewModel_HiltModules;
import com.nextgen.iptv.ui.viewmodel.DashboardViewModel;
import com.nextgen.iptv.ui.viewmodel.DashboardViewModel_HiltModules;
import com.nextgen.iptv.ui.viewmodel.LiveTvViewModel;
import com.nextgen.iptv.ui.viewmodel.LiveTvViewModel_HiltModules;
import com.nextgen.iptv.ui.viewmodel.PlayerViewModel;
import com.nextgen.iptv.ui.viewmodel.PlayerViewModel_HiltModules;
import com.nextgen.iptv.ui.viewmodel.ProviderSetupViewModel;
import com.nextgen.iptv.ui.viewmodel.ProviderSetupViewModel_HiltModules;
import com.nextgen.iptv.ui.viewmodel.SeriesDetailViewModel;
import com.nextgen.iptv.ui.viewmodel.SeriesDetailViewModel_HiltModules;
import com.nextgen.iptv.ui.viewmodel.SeriesListViewModel;
import com.nextgen.iptv.ui.viewmodel.SeriesListViewModel_HiltModules;
import com.nextgen.iptv.ui.viewmodel.SettingsViewModel;
import com.nextgen.iptv.ui.viewmodel.SettingsViewModel_HiltModules;
import com.nextgen.iptv.ui.viewmodel.VodViewModel;
import com.nextgen.iptv.ui.viewmodel.VodViewModel_HiltModules;
import com.nextgen.iptv.worker.EpgSyncWorker;
import com.nextgen.iptv.worker.EpgSyncWorker_AssistedFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import kotlinx.serialization.json.Json;
import okhttp3.OkHttpClient;

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
public final class DaggerNextGenIPTVApp_HiltComponents_SingletonC {
  private DaggerNextGenIPTVApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public NextGenIPTVApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements NextGenIPTVApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public NextGenIPTVApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements NextGenIPTVApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public NextGenIPTVApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements NextGenIPTVApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public NextGenIPTVApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements NextGenIPTVApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public NextGenIPTVApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements NextGenIPTVApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public NextGenIPTVApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements NextGenIPTVApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public NextGenIPTVApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements NextGenIPTVApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public NextGenIPTVApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends NextGenIPTVApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends NextGenIPTVApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends NextGenIPTVApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends NextGenIPTVApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>builderWithExpectedSize(9).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_LiveTvViewModel, LiveTvViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nextgen_iptv_presentation_onboarding_OnboardingViewModel, OnboardingViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_PlayerViewModel, PlayerViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_ProviderSetupViewModel, ProviderSetupViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_SeriesDetailViewModel, SeriesDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_SeriesListViewModel, SeriesListViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_VodViewModel, VodViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_nextgen_iptv_ui_viewmodel_VodViewModel = "com.nextgen.iptv.ui.viewmodel.VodViewModel";

      static String com_nextgen_iptv_presentation_onboarding_OnboardingViewModel = "com.nextgen.iptv.presentation.onboarding.OnboardingViewModel";

      static String com_nextgen_iptv_ui_viewmodel_ProviderSetupViewModel = "com.nextgen.iptv.ui.viewmodel.ProviderSetupViewModel";

      static String com_nextgen_iptv_ui_viewmodel_PlayerViewModel = "com.nextgen.iptv.ui.viewmodel.PlayerViewModel";

      static String com_nextgen_iptv_ui_viewmodel_SettingsViewModel = "com.nextgen.iptv.ui.viewmodel.SettingsViewModel";

      static String com_nextgen_iptv_ui_viewmodel_SeriesDetailViewModel = "com.nextgen.iptv.ui.viewmodel.SeriesDetailViewModel";

      static String com_nextgen_iptv_ui_viewmodel_LiveTvViewModel = "com.nextgen.iptv.ui.viewmodel.LiveTvViewModel";

      static String com_nextgen_iptv_ui_viewmodel_DashboardViewModel = "com.nextgen.iptv.ui.viewmodel.DashboardViewModel";

      static String com_nextgen_iptv_ui_viewmodel_SeriesListViewModel = "com.nextgen.iptv.ui.viewmodel.SeriesListViewModel";

      @KeepFieldType
      VodViewModel com_nextgen_iptv_ui_viewmodel_VodViewModel2;

      @KeepFieldType
      OnboardingViewModel com_nextgen_iptv_presentation_onboarding_OnboardingViewModel2;

      @KeepFieldType
      ProviderSetupViewModel com_nextgen_iptv_ui_viewmodel_ProviderSetupViewModel2;

      @KeepFieldType
      PlayerViewModel com_nextgen_iptv_ui_viewmodel_PlayerViewModel2;

      @KeepFieldType
      SettingsViewModel com_nextgen_iptv_ui_viewmodel_SettingsViewModel2;

      @KeepFieldType
      SeriesDetailViewModel com_nextgen_iptv_ui_viewmodel_SeriesDetailViewModel2;

      @KeepFieldType
      LiveTvViewModel com_nextgen_iptv_ui_viewmodel_LiveTvViewModel2;

      @KeepFieldType
      DashboardViewModel com_nextgen_iptv_ui_viewmodel_DashboardViewModel2;

      @KeepFieldType
      SeriesListViewModel com_nextgen_iptv_ui_viewmodel_SeriesListViewModel2;
    }
  }

  private static final class ViewModelCImpl extends NextGenIPTVApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<LiveTvViewModel> liveTvViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<PlayerViewModel> playerViewModelProvider;

    private Provider<ProviderSetupViewModel> providerSetupViewModelProvider;

    private Provider<SeriesDetailViewModel> seriesDetailViewModelProvider;

    private Provider<SeriesListViewModel> seriesListViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<VodViewModel> vodViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private GetProvidersWithStatusUseCase getProvidersWithStatusUseCase() {
      return new GetProvidersWithStatusUseCase(singletonCImpl.providerRepositoryImpl(), singletonCImpl.streamRepositoryImpl());
    }

    private AddProviderUseCase addProviderUseCase() {
      return new AddProviderUseCase(singletonCImpl.providerRepositoryImpl());
    }

    private SyncProviderUseCase syncProviderUseCase() {
      return new SyncProviderUseCase(singletonCImpl.providerRepositoryImpl(), singletonCImpl.categoryRepositoryImpl(), singletonCImpl.streamRepositoryImpl(), singletonCImpl.seriesRepositoryImplProvider.get(), singletonCImpl.provideXtreamCodesServiceProvider.get());
    }

    private ValidateProviderUseCase validateProviderUseCase() {
      return new ValidateProviderUseCase(singletonCImpl.provideXtreamCodesServiceProvider.get());
    }

    private GetSettingsUseCase getSettingsUseCase() {
      return new GetSettingsUseCase(singletonCImpl.settingsRepositoryImplProvider.get());
    }

    private UpdateSettingsUseCase updateSettingsUseCase() {
      return new UpdateSettingsUseCase(singletonCImpl.settingsRepositoryImplProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.liveTvViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.playerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.providerSetupViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.seriesDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.seriesListViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.vodViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(9).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_LiveTvViewModel, ((Provider) liveTvViewModelProvider)).put(LazyClassKeyProvider.com_nextgen_iptv_presentation_onboarding_OnboardingViewModel, ((Provider) onboardingViewModelProvider)).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_PlayerViewModel, ((Provider) playerViewModelProvider)).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_ProviderSetupViewModel, ((Provider) providerSetupViewModelProvider)).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_SeriesDetailViewModel, ((Provider) seriesDetailViewModelProvider)).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_SeriesListViewModel, ((Provider) seriesListViewModelProvider)).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_SettingsViewModel, ((Provider) settingsViewModelProvider)).put(LazyClassKeyProvider.com_nextgen_iptv_ui_viewmodel_VodViewModel, ((Provider) vodViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_nextgen_iptv_ui_viewmodel_LiveTvViewModel = "com.nextgen.iptv.ui.viewmodel.LiveTvViewModel";

      static String com_nextgen_iptv_ui_viewmodel_PlayerViewModel = "com.nextgen.iptv.ui.viewmodel.PlayerViewModel";

      static String com_nextgen_iptv_ui_viewmodel_SeriesDetailViewModel = "com.nextgen.iptv.ui.viewmodel.SeriesDetailViewModel";

      static String com_nextgen_iptv_ui_viewmodel_SeriesListViewModel = "com.nextgen.iptv.ui.viewmodel.SeriesListViewModel";

      static String com_nextgen_iptv_ui_viewmodel_VodViewModel = "com.nextgen.iptv.ui.viewmodel.VodViewModel";

      static String com_nextgen_iptv_ui_viewmodel_DashboardViewModel = "com.nextgen.iptv.ui.viewmodel.DashboardViewModel";

      static String com_nextgen_iptv_presentation_onboarding_OnboardingViewModel = "com.nextgen.iptv.presentation.onboarding.OnboardingViewModel";

      static String com_nextgen_iptv_ui_viewmodel_SettingsViewModel = "com.nextgen.iptv.ui.viewmodel.SettingsViewModel";

      static String com_nextgen_iptv_ui_viewmodel_ProviderSetupViewModel = "com.nextgen.iptv.ui.viewmodel.ProviderSetupViewModel";

      @KeepFieldType
      LiveTvViewModel com_nextgen_iptv_ui_viewmodel_LiveTvViewModel2;

      @KeepFieldType
      PlayerViewModel com_nextgen_iptv_ui_viewmodel_PlayerViewModel2;

      @KeepFieldType
      SeriesDetailViewModel com_nextgen_iptv_ui_viewmodel_SeriesDetailViewModel2;

      @KeepFieldType
      SeriesListViewModel com_nextgen_iptv_ui_viewmodel_SeriesListViewModel2;

      @KeepFieldType
      VodViewModel com_nextgen_iptv_ui_viewmodel_VodViewModel2;

      @KeepFieldType
      DashboardViewModel com_nextgen_iptv_ui_viewmodel_DashboardViewModel2;

      @KeepFieldType
      OnboardingViewModel com_nextgen_iptv_presentation_onboarding_OnboardingViewModel2;

      @KeepFieldType
      SettingsViewModel com_nextgen_iptv_ui_viewmodel_SettingsViewModel2;

      @KeepFieldType
      ProviderSetupViewModel com_nextgen_iptv_ui_viewmodel_ProviderSetupViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.nextgen.iptv.ui.viewmodel.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.providerRepositoryImpl(), viewModelCImpl.getProvidersWithStatusUseCase());

          case 1: // com.nextgen.iptv.ui.viewmodel.LiveTvViewModel 
          return (T) new LiveTvViewModel(singletonCImpl.categoryRepositoryImpl(), singletonCImpl.streamRepositoryImpl(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.providerRepositoryImpl());

          case 2: // com.nextgen.iptv.presentation.onboarding.OnboardingViewModel 
          return (T) new OnboardingViewModel(viewModelCImpl.addProviderUseCase(), viewModelCImpl.syncProviderUseCase(), viewModelCImpl.validateProviderUseCase(), singletonCImpl.categoryRepositoryImpl(), singletonCImpl.settingsRepositoryImplProvider.get());

          case 3: // com.nextgen.iptv.ui.viewmodel.PlayerViewModel 
          return (T) new PlayerViewModel(viewModelCImpl.savedStateHandle);

          case 4: // com.nextgen.iptv.ui.viewmodel.ProviderSetupViewModel 
          return (T) new ProviderSetupViewModel(viewModelCImpl.addProviderUseCase(), viewModelCImpl.validateProviderUseCase(), viewModelCImpl.syncProviderUseCase());

          case 5: // com.nextgen.iptv.ui.viewmodel.SeriesDetailViewModel 
          return (T) new SeriesDetailViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.seriesRepositoryImplProvider.get());

          case 6: // com.nextgen.iptv.ui.viewmodel.SeriesListViewModel 
          return (T) new SeriesListViewModel(singletonCImpl.seriesRepositoryImplProvider.get(), singletonCImpl.categoryRepositoryImpl(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.providerRepositoryImpl());

          case 7: // com.nextgen.iptv.ui.viewmodel.SettingsViewModel 
          return (T) new SettingsViewModel(viewModelCImpl.getSettingsUseCase(), viewModelCImpl.updateSettingsUseCase(), singletonCImpl.providerRepositoryImpl(), singletonCImpl.settingsRepositoryImplProvider.get(), viewModelCImpl.syncProviderUseCase());

          case 8: // com.nextgen.iptv.ui.viewmodel.VodViewModel 
          return (T) new VodViewModel(singletonCImpl.streamRepositoryImpl(), singletonCImpl.categoryRepositoryImpl(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.providerRepositoryImpl());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends NextGenIPTVApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends NextGenIPTVApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends NextGenIPTVApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<XmlTvParser> xmlTvParserProvider;

    private Provider<AppDatabase> provideAppDatabaseProvider;

    private Provider<EpgSyncWorker_AssistedFactory> epgSyncWorker_AssistedFactoryProvider;

    private Provider<SettingsRepositoryImpl> settingsRepositoryImplProvider;

    private Provider<SeriesRepositoryImpl> seriesRepositoryImplProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Json> provideJsonProvider;

    private Provider<XtreamCodesService> provideXtreamCodesServiceProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private EpgEventDao epgEventDao() {
      return DatabaseModule_ProvideEpgDaoFactory.provideEpgDao(provideAppDatabaseProvider.get());
    }

    private EpgRepositoryImpl epgRepositoryImpl() {
      return new EpgRepositoryImpl(epgEventDao());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return ImmutableMap.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>of("com.nextgen.iptv.worker.EpgSyncWorker", ((Provider) epgSyncWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private ProviderDao providerDao() {
      return DatabaseModule_ProvideProviderDaoFactory.provideProviderDao(provideAppDatabaseProvider.get());
    }

    private ProviderRepositoryImpl providerRepositoryImpl() {
      return new ProviderRepositoryImpl(providerDao());
    }

    private StreamDao streamDao() {
      return DatabaseModule_ProvideStreamDaoFactory.provideStreamDao(provideAppDatabaseProvider.get());
    }

    private StreamRepositoryImpl streamRepositoryImpl() {
      return new StreamRepositoryImpl(streamDao());
    }

    private CategoryDao categoryDao() {
      return DatabaseModule_ProvideCategoryDaoFactory.provideCategoryDao(provideAppDatabaseProvider.get());
    }

    private CategoryRepositoryImpl categoryRepositoryImpl() {
      return new CategoryRepositoryImpl(categoryDao());
    }

    private SeriesDao seriesDao() {
      return DatabaseModule_ProvideSeriesDaoFactory.provideSeriesDao(provideAppDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.xmlTvParserProvider = DoubleCheck.provider(new SwitchingProvider<XmlTvParser>(singletonCImpl, 1));
      this.provideAppDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 2));
      this.epgSyncWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<EpgSyncWorker_AssistedFactory>(singletonCImpl, 0));
      this.settingsRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<SettingsRepositoryImpl>(singletonCImpl, 3));
      this.seriesRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<SeriesRepositoryImpl>(singletonCImpl, 4));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 6));
      this.provideJsonProvider = DoubleCheck.provider(new SwitchingProvider<Json>(singletonCImpl, 7));
      this.provideXtreamCodesServiceProvider = DoubleCheck.provider(new SwitchingProvider<XtreamCodesService>(singletonCImpl, 5));
    }

    @Override
    public void injectNextGenIPTVApp(NextGenIPTVApp nextGenIPTVApp) {
      injectNextGenIPTVApp2(nextGenIPTVApp);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private NextGenIPTVApp injectNextGenIPTVApp2(NextGenIPTVApp instance) {
      NextGenIPTVApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.nextgen.iptv.worker.EpgSyncWorker_AssistedFactory 
          return (T) new EpgSyncWorker_AssistedFactory() {
            @Override
            public EpgSyncWorker create(Context context, WorkerParameters params) {
              return new EpgSyncWorker(context, params, singletonCImpl.xmlTvParserProvider.get(), singletonCImpl.epgRepositoryImpl());
            }
          };

          case 1: // com.nextgen.iptv.data.parser.XmlTvParser 
          return (T) new XmlTvParser();

          case 2: // com.nextgen.iptv.data.local.AppDatabase 
          return (T) DatabaseModule_ProvideAppDatabaseFactory.provideAppDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.nextgen.iptv.data.repository.SettingsRepositoryImpl 
          return (T) new SettingsRepositoryImpl(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.nextgen.iptv.data.repository.SeriesRepositoryImpl 
          return (T) new SeriesRepositoryImpl(singletonCImpl.seriesDao());

          case 5: // com.nextgen.iptv.data.remote.api.XtreamCodesService 
          return (T) NetworkModule_ProvideXtreamCodesServiceFactory.provideXtreamCodesService(singletonCImpl.provideOkHttpClientProvider.get(), singletonCImpl.provideJsonProvider.get());

          case 6: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 7: // kotlinx.serialization.json.Json 
          return (T) NetworkModule_ProvideJsonFactory.provideJson();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
