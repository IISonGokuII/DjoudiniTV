package com.nextgen.iptv.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.nextgen.iptv.data.parser.XmlTvParser;
import com.nextgen.iptv.domain.repository.EpgRepository;
import dagger.internal.DaggerGenerated;
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
public final class EpgSyncWorker_Factory {
  private final Provider<XmlTvParser> xmlTvParserProvider;

  private final Provider<EpgRepository> epgRepositoryProvider;

  public EpgSyncWorker_Factory(Provider<XmlTvParser> xmlTvParserProvider,
      Provider<EpgRepository> epgRepositoryProvider) {
    this.xmlTvParserProvider = xmlTvParserProvider;
    this.epgRepositoryProvider = epgRepositoryProvider;
  }

  public EpgSyncWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, xmlTvParserProvider.get(), epgRepositoryProvider.get());
  }

  public static EpgSyncWorker_Factory create(Provider<XmlTvParser> xmlTvParserProvider,
      Provider<EpgRepository> epgRepositoryProvider) {
    return new EpgSyncWorker_Factory(xmlTvParserProvider, epgRepositoryProvider);
  }

  public static EpgSyncWorker newInstance(Context context, WorkerParameters params,
      XmlTvParser xmlTvParser, EpgRepository epgRepository) {
    return new EpgSyncWorker(context, params, xmlTvParser, epgRepository);
  }
}
