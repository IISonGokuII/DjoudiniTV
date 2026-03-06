package com.nextgen.iptv.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.nextgen.iptv.data.local.dao.CategoryDao;
import com.nextgen.iptv.data.local.dao.CategoryDao_Impl;
import com.nextgen.iptv.data.local.dao.EpgEventDao;
import com.nextgen.iptv.data.local.dao.EpgEventDao_Impl;
import com.nextgen.iptv.data.local.dao.FavoriteDao;
import com.nextgen.iptv.data.local.dao.FavoriteDao_Impl;
import com.nextgen.iptv.data.local.dao.ProviderDao;
import com.nextgen.iptv.data.local.dao.ProviderDao_Impl;
import com.nextgen.iptv.data.local.dao.SeriesDao;
import com.nextgen.iptv.data.local.dao.SeriesDao_Impl;
import com.nextgen.iptv.data.local.dao.StreamDao;
import com.nextgen.iptv.data.local.dao.StreamDao_Impl;
import com.nextgen.iptv.data.local.dao.VodProgressDao;
import com.nextgen.iptv.data.local.dao.VodProgressDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ProviderDao _providerDao;

  private volatile CategoryDao _categoryDao;

  private volatile StreamDao _streamDao;

  private volatile EpgEventDao _epgEventDao;

  private volatile VodProgressDao _vodProgressDao;

  private volatile FavoriteDao _favoriteDao;

  private volatile SeriesDao _seriesDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `providers` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `serverUrl` TEXT NOT NULL, `username` TEXT, `password` TEXT, `lastSync` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`id` TEXT NOT NULL, `providerId` TEXT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_categories_providerId` ON `categories` (`providerId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_categories_type` ON `categories` (`type`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `streams` (`id` TEXT NOT NULL, `categoryId` TEXT NOT NULL, `providerId` TEXT NOT NULL, `name` TEXT NOT NULL, `streamUrl` TEXT NOT NULL, `logoUrl` TEXT, `epgChannelId` TEXT, `type` TEXT NOT NULL, `plot` TEXT, `cast` TEXT, `director` TEXT, `genre` TEXT, `rating` TEXT, `rating5Based` REAL, `releaseDate` TEXT, `durationSecs` INTEGER, `duration` TEXT, `backdropUrl` TEXT, `youtubeTrailer` TEXT, `added` TEXT, `containerExtension` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_streams_categoryId` ON `streams` (`categoryId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_streams_providerId` ON `streams` (`providerId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_streams_epgChannelId` ON `streams` (`epgChannelId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `epg_events` (`id` TEXT NOT NULL, `channelId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `startTime` INTEGER NOT NULL, `endTime` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_epg_events_channelId` ON `epg_events` (`channelId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_epg_events_startTime` ON `epg_events` (`startTime`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vod_progress` (`streamId` TEXT NOT NULL, `progressMs` INTEGER NOT NULL, `durationMs` INTEGER NOT NULL, `lastWatched` INTEGER NOT NULL, PRIMARY KEY(`streamId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `favorites` (`streamId` TEXT NOT NULL, `streamType` TEXT NOT NULL, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`streamId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `series` (`id` TEXT NOT NULL, `providerId` TEXT NOT NULL, `categoryId` TEXT NOT NULL, `name` TEXT NOT NULL, `plot` TEXT, `posterUrl` TEXT, `backdropUrl` TEXT, `rating` TEXT, `releaseDate` TEXT, `genre` TEXT, `cast` TEXT, `director` TEXT, `episodeRunTime` TEXT, `totalSeasons` INTEGER NOT NULL, `totalEpisodes` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_series_providerId` ON `series` (`providerId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_series_categoryId` ON `series` (`categoryId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `seasons` (`id` TEXT NOT NULL, `seriesId` TEXT NOT NULL, `seasonNumber` INTEGER NOT NULL, `name` TEXT NOT NULL, `episodeCount` INTEGER NOT NULL, `posterUrl` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_seasons_seriesId` ON `seasons` (`seriesId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `episodes` (`id` TEXT NOT NULL, `seriesId` TEXT NOT NULL, `seasonId` TEXT NOT NULL, `episodeNumber` INTEGER NOT NULL, `seasonNumber` INTEGER NOT NULL, `name` TEXT NOT NULL, `plot` TEXT, `posterUrl` TEXT, `durationSec` INTEGER, `streamUrl` TEXT NOT NULL, `containerExtension` TEXT, `added` TEXT, `rating` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_episodes_seriesId` ON `episodes` (`seriesId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_episodes_seasonId` ON `episodes` (`seasonId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'cb1f3efa15bb463b776b6a0c7943ddb8')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `providers`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `streams`");
        db.execSQL("DROP TABLE IF EXISTS `epg_events`");
        db.execSQL("DROP TABLE IF EXISTS `vod_progress`");
        db.execSQL("DROP TABLE IF EXISTS `favorites`");
        db.execSQL("DROP TABLE IF EXISTS `series`");
        db.execSQL("DROP TABLE IF EXISTS `seasons`");
        db.execSQL("DROP TABLE IF EXISTS `episodes`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsProviders = new HashMap<String, TableInfo.Column>(7);
        _columnsProviders.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProviders.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProviders.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProviders.put("serverUrl", new TableInfo.Column("serverUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProviders.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProviders.put("password", new TableInfo.Column("password", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProviders.put("lastSync", new TableInfo.Column("lastSync", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProviders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProviders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProviders = new TableInfo("providers", _columnsProviders, _foreignKeysProviders, _indicesProviders);
        final TableInfo _existingProviders = TableInfo.read(db, "providers");
        if (!_infoProviders.equals(_existingProviders)) {
          return new RoomOpenHelper.ValidationResult(false, "providers(com.nextgen.iptv.data.local.entity.ProviderEntity).\n"
                  + " Expected:\n" + _infoProviders + "\n"
                  + " Found:\n" + _existingProviders);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(4);
        _columnsCategories.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("providerId", new TableInfo.Column("providerId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(2);
        _indicesCategories.add(new TableInfo.Index("index_categories_providerId", false, Arrays.asList("providerId"), Arrays.asList("ASC")));
        _indicesCategories.add(new TableInfo.Index("index_categories_type", false, Arrays.asList("type"), Arrays.asList("ASC")));
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(com.nextgen.iptv.data.local.entity.CategoryEntity).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsStreams = new HashMap<String, TableInfo.Column>(21);
        _columnsStreams.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("categoryId", new TableInfo.Column("categoryId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("providerId", new TableInfo.Column("providerId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("streamUrl", new TableInfo.Column("streamUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("logoUrl", new TableInfo.Column("logoUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("epgChannelId", new TableInfo.Column("epgChannelId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("plot", new TableInfo.Column("plot", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("cast", new TableInfo.Column("cast", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("director", new TableInfo.Column("director", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("genre", new TableInfo.Column("genre", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("rating", new TableInfo.Column("rating", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("rating5Based", new TableInfo.Column("rating5Based", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("releaseDate", new TableInfo.Column("releaseDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("durationSecs", new TableInfo.Column("durationSecs", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("duration", new TableInfo.Column("duration", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("backdropUrl", new TableInfo.Column("backdropUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("youtubeTrailer", new TableInfo.Column("youtubeTrailer", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("added", new TableInfo.Column("added", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStreams.put("containerExtension", new TableInfo.Column("containerExtension", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStreams = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStreams = new HashSet<TableInfo.Index>(3);
        _indicesStreams.add(new TableInfo.Index("index_streams_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        _indicesStreams.add(new TableInfo.Index("index_streams_providerId", false, Arrays.asList("providerId"), Arrays.asList("ASC")));
        _indicesStreams.add(new TableInfo.Index("index_streams_epgChannelId", false, Arrays.asList("epgChannelId"), Arrays.asList("ASC")));
        final TableInfo _infoStreams = new TableInfo("streams", _columnsStreams, _foreignKeysStreams, _indicesStreams);
        final TableInfo _existingStreams = TableInfo.read(db, "streams");
        if (!_infoStreams.equals(_existingStreams)) {
          return new RoomOpenHelper.ValidationResult(false, "streams(com.nextgen.iptv.data.local.entity.StreamEntity).\n"
                  + " Expected:\n" + _infoStreams + "\n"
                  + " Found:\n" + _existingStreams);
        }
        final HashMap<String, TableInfo.Column> _columnsEpgEvents = new HashMap<String, TableInfo.Column>(6);
        _columnsEpgEvents.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpgEvents.put("channelId", new TableInfo.Column("channelId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpgEvents.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpgEvents.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpgEvents.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpgEvents.put("endTime", new TableInfo.Column("endTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEpgEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEpgEvents = new HashSet<TableInfo.Index>(2);
        _indicesEpgEvents.add(new TableInfo.Index("index_epg_events_channelId", false, Arrays.asList("channelId"), Arrays.asList("ASC")));
        _indicesEpgEvents.add(new TableInfo.Index("index_epg_events_startTime", false, Arrays.asList("startTime"), Arrays.asList("ASC")));
        final TableInfo _infoEpgEvents = new TableInfo("epg_events", _columnsEpgEvents, _foreignKeysEpgEvents, _indicesEpgEvents);
        final TableInfo _existingEpgEvents = TableInfo.read(db, "epg_events");
        if (!_infoEpgEvents.equals(_existingEpgEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "epg_events(com.nextgen.iptv.data.local.entity.EpgEventEntity).\n"
                  + " Expected:\n" + _infoEpgEvents + "\n"
                  + " Found:\n" + _existingEpgEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsVodProgress = new HashMap<String, TableInfo.Column>(4);
        _columnsVodProgress.put("streamId", new TableInfo.Column("streamId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodProgress.put("progressMs", new TableInfo.Column("progressMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodProgress.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodProgress.put("lastWatched", new TableInfo.Column("lastWatched", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVodProgress = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVodProgress = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVodProgress = new TableInfo("vod_progress", _columnsVodProgress, _foreignKeysVodProgress, _indicesVodProgress);
        final TableInfo _existingVodProgress = TableInfo.read(db, "vod_progress");
        if (!_infoVodProgress.equals(_existingVodProgress)) {
          return new RoomOpenHelper.ValidationResult(false, "vod_progress(com.nextgen.iptv.data.local.entity.VodProgressEntity).\n"
                  + " Expected:\n" + _infoVodProgress + "\n"
                  + " Found:\n" + _existingVodProgress);
        }
        final HashMap<String, TableInfo.Column> _columnsFavorites = new HashMap<String, TableInfo.Column>(3);
        _columnsFavorites.put("streamId", new TableInfo.Column("streamId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavorites.put("streamType", new TableInfo.Column("streamType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavorites.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavorites = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavorites = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavorites = new TableInfo("favorites", _columnsFavorites, _foreignKeysFavorites, _indicesFavorites);
        final TableInfo _existingFavorites = TableInfo.read(db, "favorites");
        if (!_infoFavorites.equals(_existingFavorites)) {
          return new RoomOpenHelper.ValidationResult(false, "favorites(com.nextgen.iptv.data.local.entity.FavoriteEntity).\n"
                  + " Expected:\n" + _infoFavorites + "\n"
                  + " Found:\n" + _existingFavorites);
        }
        final HashMap<String, TableInfo.Column> _columnsSeries = new HashMap<String, TableInfo.Column>(15);
        _columnsSeries.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("providerId", new TableInfo.Column("providerId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("categoryId", new TableInfo.Column("categoryId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("plot", new TableInfo.Column("plot", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("posterUrl", new TableInfo.Column("posterUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("backdropUrl", new TableInfo.Column("backdropUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("rating", new TableInfo.Column("rating", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("releaseDate", new TableInfo.Column("releaseDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("genre", new TableInfo.Column("genre", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("cast", new TableInfo.Column("cast", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("director", new TableInfo.Column("director", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("episodeRunTime", new TableInfo.Column("episodeRunTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("totalSeasons", new TableInfo.Column("totalSeasons", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeries.put("totalEpisodes", new TableInfo.Column("totalEpisodes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSeries = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSeries = new HashSet<TableInfo.Index>(2);
        _indicesSeries.add(new TableInfo.Index("index_series_providerId", false, Arrays.asList("providerId"), Arrays.asList("ASC")));
        _indicesSeries.add(new TableInfo.Index("index_series_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        final TableInfo _infoSeries = new TableInfo("series", _columnsSeries, _foreignKeysSeries, _indicesSeries);
        final TableInfo _existingSeries = TableInfo.read(db, "series");
        if (!_infoSeries.equals(_existingSeries)) {
          return new RoomOpenHelper.ValidationResult(false, "series(com.nextgen.iptv.data.local.entity.SeriesEntity).\n"
                  + " Expected:\n" + _infoSeries + "\n"
                  + " Found:\n" + _existingSeries);
        }
        final HashMap<String, TableInfo.Column> _columnsSeasons = new HashMap<String, TableInfo.Column>(6);
        _columnsSeasons.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeasons.put("seriesId", new TableInfo.Column("seriesId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeasons.put("seasonNumber", new TableInfo.Column("seasonNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeasons.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeasons.put("episodeCount", new TableInfo.Column("episodeCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeasons.put("posterUrl", new TableInfo.Column("posterUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSeasons = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSeasons = new HashSet<TableInfo.Index>(1);
        _indicesSeasons.add(new TableInfo.Index("index_seasons_seriesId", false, Arrays.asList("seriesId"), Arrays.asList("ASC")));
        final TableInfo _infoSeasons = new TableInfo("seasons", _columnsSeasons, _foreignKeysSeasons, _indicesSeasons);
        final TableInfo _existingSeasons = TableInfo.read(db, "seasons");
        if (!_infoSeasons.equals(_existingSeasons)) {
          return new RoomOpenHelper.ValidationResult(false, "seasons(com.nextgen.iptv.data.local.entity.SeasonEntity).\n"
                  + " Expected:\n" + _infoSeasons + "\n"
                  + " Found:\n" + _existingSeasons);
        }
        final HashMap<String, TableInfo.Column> _columnsEpisodes = new HashMap<String, TableInfo.Column>(13);
        _columnsEpisodes.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("seriesId", new TableInfo.Column("seriesId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("seasonId", new TableInfo.Column("seasonId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("episodeNumber", new TableInfo.Column("episodeNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("seasonNumber", new TableInfo.Column("seasonNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("plot", new TableInfo.Column("plot", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("posterUrl", new TableInfo.Column("posterUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("durationSec", new TableInfo.Column("durationSec", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("streamUrl", new TableInfo.Column("streamUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("containerExtension", new TableInfo.Column("containerExtension", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("added", new TableInfo.Column("added", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodes.put("rating", new TableInfo.Column("rating", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEpisodes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEpisodes = new HashSet<TableInfo.Index>(2);
        _indicesEpisodes.add(new TableInfo.Index("index_episodes_seriesId", false, Arrays.asList("seriesId"), Arrays.asList("ASC")));
        _indicesEpisodes.add(new TableInfo.Index("index_episodes_seasonId", false, Arrays.asList("seasonId"), Arrays.asList("ASC")));
        final TableInfo _infoEpisodes = new TableInfo("episodes", _columnsEpisodes, _foreignKeysEpisodes, _indicesEpisodes);
        final TableInfo _existingEpisodes = TableInfo.read(db, "episodes");
        if (!_infoEpisodes.equals(_existingEpisodes)) {
          return new RoomOpenHelper.ValidationResult(false, "episodes(com.nextgen.iptv.data.local.entity.EpisodeEntity).\n"
                  + " Expected:\n" + _infoEpisodes + "\n"
                  + " Found:\n" + _existingEpisodes);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "cb1f3efa15bb463b776b6a0c7943ddb8", "16ab7a5b16c70b1734d73b94cf50edb3");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "providers","categories","streams","epg_events","vod_progress","favorites","series","seasons","episodes");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `providers`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `streams`");
      _db.execSQL("DELETE FROM `epg_events`");
      _db.execSQL("DELETE FROM `vod_progress`");
      _db.execSQL("DELETE FROM `favorites`");
      _db.execSQL("DELETE FROM `series`");
      _db.execSQL("DELETE FROM `seasons`");
      _db.execSQL("DELETE FROM `episodes`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ProviderDao.class, ProviderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CategoryDao.class, CategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StreamDao.class, StreamDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EpgEventDao.class, EpgEventDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VodProgressDao.class, VodProgressDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FavoriteDao.class, FavoriteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SeriesDao.class, SeriesDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ProviderDao providerDao() {
    if (_providerDao != null) {
      return _providerDao;
    } else {
      synchronized(this) {
        if(_providerDao == null) {
          _providerDao = new ProviderDao_Impl(this);
        }
        return _providerDao;
      }
    }
  }

  @Override
  public CategoryDao categoryDao() {
    if (_categoryDao != null) {
      return _categoryDao;
    } else {
      synchronized(this) {
        if(_categoryDao == null) {
          _categoryDao = new CategoryDao_Impl(this);
        }
        return _categoryDao;
      }
    }
  }

  @Override
  public StreamDao streamDao() {
    if (_streamDao != null) {
      return _streamDao;
    } else {
      synchronized(this) {
        if(_streamDao == null) {
          _streamDao = new StreamDao_Impl(this);
        }
        return _streamDao;
      }
    }
  }

  @Override
  public EpgEventDao epgEventDao() {
    if (_epgEventDao != null) {
      return _epgEventDao;
    } else {
      synchronized(this) {
        if(_epgEventDao == null) {
          _epgEventDao = new EpgEventDao_Impl(this);
        }
        return _epgEventDao;
      }
    }
  }

  @Override
  public VodProgressDao vodProgressDao() {
    if (_vodProgressDao != null) {
      return _vodProgressDao;
    } else {
      synchronized(this) {
        if(_vodProgressDao == null) {
          _vodProgressDao = new VodProgressDao_Impl(this);
        }
        return _vodProgressDao;
      }
    }
  }

  @Override
  public FavoriteDao favoriteDao() {
    if (_favoriteDao != null) {
      return _favoriteDao;
    } else {
      synchronized(this) {
        if(_favoriteDao == null) {
          _favoriteDao = new FavoriteDao_Impl(this);
        }
        return _favoriteDao;
      }
    }
  }

  @Override
  public SeriesDao seriesDao() {
    if (_seriesDao != null) {
      return _seriesDao;
    } else {
      synchronized(this) {
        if(_seriesDao == null) {
          _seriesDao = new SeriesDao_Impl(this);
        }
        return _seriesDao;
      }
    }
  }
}
