package com.nextgen.iptv.data.parser;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class XmlTvParser_Factory implements Factory<XmlTvParser> {
  @Override
  public XmlTvParser get() {
    return newInstance();
  }

  public static XmlTvParser_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static XmlTvParser newInstance() {
    return new XmlTvParser();
  }

  private static final class InstanceHolder {
    private static final XmlTvParser_Factory INSTANCE = new XmlTvParser_Factory();
  }
}
