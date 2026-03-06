package com.nextgen.iptv.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nextgen.iptv.data.local.entity.EpisodeEntity;
import com.nextgen.iptv.data.local.entity.SeasonEntity;
import com.nextgen.iptv.data.local.entity.SeriesEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SeriesDao_Impl implements SeriesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SeriesEntity> __insertionAdapterOfSeriesEntity;

  private final EntityInsertionAdapter<SeasonEntity> __insertionAdapterOfSeasonEntity;

  private final EntityInsertionAdapter<EpisodeEntity> __insertionAdapterOfEpisodeEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSeriesByProvider;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSeasonsByProvider;

  private final SharedSQLiteStatement __preparedStmtOfDeleteEpisodesByProvider;

  public SeriesDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSeriesEntity = new EntityInsertionAdapter<SeriesEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `series` (`id`,`providerId`,`categoryId`,`name`,`plot`,`posterUrl`,`backdropUrl`,`rating`,`releaseDate`,`genre`,`cast`,`director`,`episodeRunTime`,`totalSeasons`,`totalEpisodes`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SeriesEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProviderId());
        statement.bindString(3, entity.getCategoryId());
        statement.bindString(4, entity.getName());
        if (entity.getPlot() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPlot());
        }
        if (entity.getPosterUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPosterUrl());
        }
        if (entity.getBackdropUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getBackdropUrl());
        }
        if (entity.getRating() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getRating());
        }
        if (entity.getReleaseDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getReleaseDate());
        }
        if (entity.getGenre() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getGenre());
        }
        if (entity.getCast() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCast());
        }
        if (entity.getDirector() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getDirector());
        }
        if (entity.getEpisodeRunTime() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getEpisodeRunTime());
        }
        statement.bindLong(14, entity.getTotalSeasons());
        statement.bindLong(15, entity.getTotalEpisodes());
      }
    };
    this.__insertionAdapterOfSeasonEntity = new EntityInsertionAdapter<SeasonEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `seasons` (`id`,`seriesId`,`seasonNumber`,`name`,`episodeCount`,`posterUrl`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SeasonEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSeriesId());
        statement.bindLong(3, entity.getSeasonNumber());
        statement.bindString(4, entity.getName());
        statement.bindLong(5, entity.getEpisodeCount());
        if (entity.getPosterUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPosterUrl());
        }
      }
    };
    this.__insertionAdapterOfEpisodeEntity = new EntityInsertionAdapter<EpisodeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `episodes` (`id`,`seriesId`,`seasonId`,`episodeNumber`,`seasonNumber`,`name`,`plot`,`posterUrl`,`durationSec`,`streamUrl`,`containerExtension`,`added`,`rating`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EpisodeEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSeriesId());
        statement.bindString(3, entity.getSeasonId());
        statement.bindLong(4, entity.getEpisodeNumber());
        statement.bindLong(5, entity.getSeasonNumber());
        statement.bindString(6, entity.getName());
        if (entity.getPlot() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPlot());
        }
        if (entity.getPosterUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPosterUrl());
        }
        if (entity.getDurationSec() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getDurationSec());
        }
        statement.bindString(10, entity.getStreamUrl());
        if (entity.getContainerExtension() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getContainerExtension());
        }
        if (entity.getAdded() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getAdded());
        }
        if (entity.getRating() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getRating());
        }
      }
    };
    this.__preparedStmtOfDeleteSeriesByProvider = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM series WHERE providerId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteSeasonsByProvider = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM seasons WHERE seriesId IN (SELECT id FROM series WHERE providerId = ?)";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteEpisodesByProvider = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM episodes WHERE seriesId IN (SELECT id FROM series WHERE providerId = ?)";
        return _query;
      }
    };
  }

  @Override
  public Object insertSeries(final List<SeriesEntity> series,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSeriesEntity.insert(series);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertSeries(final SeriesEntity series,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSeriesEntity.insert(series);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertSeasons(final List<SeasonEntity> seasons,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSeasonEntity.insert(seasons);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertEpisodes(final List<EpisodeEntity> episodes,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEpisodeEntity.insert(episodes);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSeriesByProvider(final String providerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSeriesByProvider.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, providerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteSeriesByProvider.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSeasonsByProvider(final String providerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSeasonsByProvider.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, providerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteSeasonsByProvider.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEpisodesByProvider(final String providerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteEpisodesByProvider.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, providerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteEpisodesByProvider.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getSeriesById(final String seriesId,
      final Continuation<? super SeriesEntity> $completion) {
    final String _sql = "SELECT * FROM series WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, seriesId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SeriesEntity>() {
      @Override
      @Nullable
      public SeriesEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfEpisodeRunTime = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeRunTime");
          final int _cursorIndexOfTotalSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSeasons");
          final int _cursorIndexOfTotalEpisodes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalEpisodes");
          final SeriesEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            }
            final String _tmpPosterUrl;
            if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
              _tmpPosterUrl = null;
            } else {
              _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpCast;
            if (_cursor.isNull(_cursorIndexOfCast)) {
              _tmpCast = null;
            } else {
              _tmpCast = _cursor.getString(_cursorIndexOfCast);
            }
            final String _tmpDirector;
            if (_cursor.isNull(_cursorIndexOfDirector)) {
              _tmpDirector = null;
            } else {
              _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            }
            final String _tmpEpisodeRunTime;
            if (_cursor.isNull(_cursorIndexOfEpisodeRunTime)) {
              _tmpEpisodeRunTime = null;
            } else {
              _tmpEpisodeRunTime = _cursor.getString(_cursorIndexOfEpisodeRunTime);
            }
            final int _tmpTotalSeasons;
            _tmpTotalSeasons = _cursor.getInt(_cursorIndexOfTotalSeasons);
            final int _tmpTotalEpisodes;
            _tmpTotalEpisodes = _cursor.getInt(_cursorIndexOfTotalEpisodes);
            _result = new SeriesEntity(_tmpId,_tmpProviderId,_tmpCategoryId,_tmpName,_tmpPlot,_tmpPosterUrl,_tmpBackdropUrl,_tmpRating,_tmpReleaseDate,_tmpGenre,_tmpCast,_tmpDirector,_tmpEpisodeRunTime,_tmpTotalSeasons,_tmpTotalEpisodes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SeriesEntity>> getAllSeries() {
    final String _sql = "SELECT * FROM series ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"series"}, new Callable<List<SeriesEntity>>() {
      @Override
      @NonNull
      public List<SeriesEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfEpisodeRunTime = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeRunTime");
          final int _cursorIndexOfTotalSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSeasons");
          final int _cursorIndexOfTotalEpisodes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalEpisodes");
          final List<SeriesEntity> _result = new ArrayList<SeriesEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SeriesEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            }
            final String _tmpPosterUrl;
            if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
              _tmpPosterUrl = null;
            } else {
              _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpCast;
            if (_cursor.isNull(_cursorIndexOfCast)) {
              _tmpCast = null;
            } else {
              _tmpCast = _cursor.getString(_cursorIndexOfCast);
            }
            final String _tmpDirector;
            if (_cursor.isNull(_cursorIndexOfDirector)) {
              _tmpDirector = null;
            } else {
              _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            }
            final String _tmpEpisodeRunTime;
            if (_cursor.isNull(_cursorIndexOfEpisodeRunTime)) {
              _tmpEpisodeRunTime = null;
            } else {
              _tmpEpisodeRunTime = _cursor.getString(_cursorIndexOfEpisodeRunTime);
            }
            final int _tmpTotalSeasons;
            _tmpTotalSeasons = _cursor.getInt(_cursorIndexOfTotalSeasons);
            final int _tmpTotalEpisodes;
            _tmpTotalEpisodes = _cursor.getInt(_cursorIndexOfTotalEpisodes);
            _item = new SeriesEntity(_tmpId,_tmpProviderId,_tmpCategoryId,_tmpName,_tmpPlot,_tmpPosterUrl,_tmpBackdropUrl,_tmpRating,_tmpReleaseDate,_tmpGenre,_tmpCast,_tmpDirector,_tmpEpisodeRunTime,_tmpTotalSeasons,_tmpTotalEpisodes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<SeriesEntity>> getSeriesByCategory(final String categoryId) {
    final String _sql = "SELECT * FROM series WHERE categoryId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, categoryId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"series"}, new Callable<List<SeriesEntity>>() {
      @Override
      @NonNull
      public List<SeriesEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfEpisodeRunTime = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeRunTime");
          final int _cursorIndexOfTotalSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSeasons");
          final int _cursorIndexOfTotalEpisodes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalEpisodes");
          final List<SeriesEntity> _result = new ArrayList<SeriesEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SeriesEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            }
            final String _tmpPosterUrl;
            if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
              _tmpPosterUrl = null;
            } else {
              _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpCast;
            if (_cursor.isNull(_cursorIndexOfCast)) {
              _tmpCast = null;
            } else {
              _tmpCast = _cursor.getString(_cursorIndexOfCast);
            }
            final String _tmpDirector;
            if (_cursor.isNull(_cursorIndexOfDirector)) {
              _tmpDirector = null;
            } else {
              _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            }
            final String _tmpEpisodeRunTime;
            if (_cursor.isNull(_cursorIndexOfEpisodeRunTime)) {
              _tmpEpisodeRunTime = null;
            } else {
              _tmpEpisodeRunTime = _cursor.getString(_cursorIndexOfEpisodeRunTime);
            }
            final int _tmpTotalSeasons;
            _tmpTotalSeasons = _cursor.getInt(_cursorIndexOfTotalSeasons);
            final int _tmpTotalEpisodes;
            _tmpTotalEpisodes = _cursor.getInt(_cursorIndexOfTotalEpisodes);
            _item = new SeriesEntity(_tmpId,_tmpProviderId,_tmpCategoryId,_tmpName,_tmpPlot,_tmpPosterUrl,_tmpBackdropUrl,_tmpRating,_tmpReleaseDate,_tmpGenre,_tmpCast,_tmpDirector,_tmpEpisodeRunTime,_tmpTotalSeasons,_tmpTotalEpisodes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<SeriesEntity>> getSeriesByProvider(final String providerId) {
    final String _sql = "SELECT * FROM series WHERE providerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, providerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"series"}, new Callable<List<SeriesEntity>>() {
      @Override
      @NonNull
      public List<SeriesEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfEpisodeRunTime = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeRunTime");
          final int _cursorIndexOfTotalSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSeasons");
          final int _cursorIndexOfTotalEpisodes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalEpisodes");
          final List<SeriesEntity> _result = new ArrayList<SeriesEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SeriesEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            }
            final String _tmpPosterUrl;
            if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
              _tmpPosterUrl = null;
            } else {
              _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpCast;
            if (_cursor.isNull(_cursorIndexOfCast)) {
              _tmpCast = null;
            } else {
              _tmpCast = _cursor.getString(_cursorIndexOfCast);
            }
            final String _tmpDirector;
            if (_cursor.isNull(_cursorIndexOfDirector)) {
              _tmpDirector = null;
            } else {
              _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            }
            final String _tmpEpisodeRunTime;
            if (_cursor.isNull(_cursorIndexOfEpisodeRunTime)) {
              _tmpEpisodeRunTime = null;
            } else {
              _tmpEpisodeRunTime = _cursor.getString(_cursorIndexOfEpisodeRunTime);
            }
            final int _tmpTotalSeasons;
            _tmpTotalSeasons = _cursor.getInt(_cursorIndexOfTotalSeasons);
            final int _tmpTotalEpisodes;
            _tmpTotalEpisodes = _cursor.getInt(_cursorIndexOfTotalEpisodes);
            _item = new SeriesEntity(_tmpId,_tmpProviderId,_tmpCategoryId,_tmpName,_tmpPlot,_tmpPosterUrl,_tmpBackdropUrl,_tmpRating,_tmpReleaseDate,_tmpGenre,_tmpCast,_tmpDirector,_tmpEpisodeRunTime,_tmpTotalSeasons,_tmpTotalEpisodes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<SeriesEntity>> searchSeries(final String query) {
    final String _sql = "SELECT * FROM series WHERE name LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"series"}, new Callable<List<SeriesEntity>>() {
      @Override
      @NonNull
      public List<SeriesEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfEpisodeRunTime = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeRunTime");
          final int _cursorIndexOfTotalSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSeasons");
          final int _cursorIndexOfTotalEpisodes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalEpisodes");
          final List<SeriesEntity> _result = new ArrayList<SeriesEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SeriesEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            }
            final String _tmpPosterUrl;
            if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
              _tmpPosterUrl = null;
            } else {
              _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpCast;
            if (_cursor.isNull(_cursorIndexOfCast)) {
              _tmpCast = null;
            } else {
              _tmpCast = _cursor.getString(_cursorIndexOfCast);
            }
            final String _tmpDirector;
            if (_cursor.isNull(_cursorIndexOfDirector)) {
              _tmpDirector = null;
            } else {
              _tmpDirector = _cursor.getString(_cursorIndexOfDirector);
            }
            final String _tmpEpisodeRunTime;
            if (_cursor.isNull(_cursorIndexOfEpisodeRunTime)) {
              _tmpEpisodeRunTime = null;
            } else {
              _tmpEpisodeRunTime = _cursor.getString(_cursorIndexOfEpisodeRunTime);
            }
            final int _tmpTotalSeasons;
            _tmpTotalSeasons = _cursor.getInt(_cursorIndexOfTotalSeasons);
            final int _tmpTotalEpisodes;
            _tmpTotalEpisodes = _cursor.getInt(_cursorIndexOfTotalEpisodes);
            _item = new SeriesEntity(_tmpId,_tmpProviderId,_tmpCategoryId,_tmpName,_tmpPlot,_tmpPosterUrl,_tmpBackdropUrl,_tmpRating,_tmpReleaseDate,_tmpGenre,_tmpCast,_tmpDirector,_tmpEpisodeRunTime,_tmpTotalSeasons,_tmpTotalEpisodes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<SeasonEntity>> getSeasonsBySeries(final String seriesId) {
    final String _sql = "SELECT * FROM seasons WHERE seriesId = ? ORDER BY seasonNumber";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, seriesId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"seasons"}, new Callable<List<SeasonEntity>>() {
      @Override
      @NonNull
      public List<SeasonEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSeriesId = CursorUtil.getColumnIndexOrThrow(_cursor, "seriesId");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEpisodeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeCount");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
          final List<SeasonEntity> _result = new ArrayList<SeasonEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SeasonEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSeriesId;
            _tmpSeriesId = _cursor.getString(_cursorIndexOfSeriesId);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpEpisodeCount;
            _tmpEpisodeCount = _cursor.getInt(_cursorIndexOfEpisodeCount);
            final String _tmpPosterUrl;
            if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
              _tmpPosterUrl = null;
            } else {
              _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            }
            _item = new SeasonEntity(_tmpId,_tmpSeriesId,_tmpSeasonNumber,_tmpName,_tmpEpisodeCount,_tmpPosterUrl);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSeasonById(final String seasonId,
      final Continuation<? super SeasonEntity> $completion) {
    final String _sql = "SELECT * FROM seasons WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, seasonId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SeasonEntity>() {
      @Override
      @Nullable
      public SeasonEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSeriesId = CursorUtil.getColumnIndexOrThrow(_cursor, "seriesId");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEpisodeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeCount");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
          final SeasonEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSeriesId;
            _tmpSeriesId = _cursor.getString(_cursorIndexOfSeriesId);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpEpisodeCount;
            _tmpEpisodeCount = _cursor.getInt(_cursorIndexOfEpisodeCount);
            final String _tmpPosterUrl;
            if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
              _tmpPosterUrl = null;
            } else {
              _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            }
            _result = new SeasonEntity(_tmpId,_tmpSeriesId,_tmpSeasonNumber,_tmpName,_tmpEpisodeCount,_tmpPosterUrl);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<EpisodeEntity>> getEpisodesBySeries(final String seriesId) {
    final String _sql = "SELECT * FROM episodes WHERE seriesId = ? ORDER BY seasonNumber, episodeNumber";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, seriesId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"episodes"}, new Callable<List<EpisodeEntity>>() {
      @Override
      @NonNull
      public List<EpisodeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSeriesId = CursorUtil.getColumnIndexOrThrow(_cursor, "seriesId");
          final int _cursorIndexOfSeasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonId");
          final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
          final int _cursorIndexOfDurationSec = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSec");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final List<EpisodeEntity> _result = new ArrayList<EpisodeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EpisodeEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSeriesId;
            _tmpSeriesId = _cursor.getString(_cursorIndexOfSeriesId);
            final String _tmpSeasonId;
            _tmpSeasonId = _cursor.getString(_cursorIndexOfSeasonId);
            final int _tmpEpisodeNumber;
            _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            }
            final String _tmpPosterUrl;
            if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
              _tmpPosterUrl = null;
            } else {
              _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            }
            final Integer _tmpDurationSec;
            if (_cursor.isNull(_cursorIndexOfDurationSec)) {
              _tmpDurationSec = null;
            } else {
              _tmpDurationSec = _cursor.getInt(_cursorIndexOfDurationSec);
            }
            final String _tmpStreamUrl;
            _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            final String _tmpAdded;
            if (_cursor.isNull(_cursorIndexOfAdded)) {
              _tmpAdded = null;
            } else {
              _tmpAdded = _cursor.getString(_cursorIndexOfAdded);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            _item = new EpisodeEntity(_tmpId,_tmpSeriesId,_tmpSeasonId,_tmpEpisodeNumber,_tmpSeasonNumber,_tmpName,_tmpPlot,_tmpPosterUrl,_tmpDurationSec,_tmpStreamUrl,_tmpContainerExtension,_tmpAdded,_tmpRating);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<EpisodeEntity>> getEpisodesBySeason(final String seasonId) {
    final String _sql = "SELECT * FROM episodes WHERE seasonId = ? ORDER BY episodeNumber";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, seasonId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"episodes"}, new Callable<List<EpisodeEntity>>() {
      @Override
      @NonNull
      public List<EpisodeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSeriesId = CursorUtil.getColumnIndexOrThrow(_cursor, "seriesId");
          final int _cursorIndexOfSeasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonId");
          final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
          final int _cursorIndexOfDurationSec = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSec");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final List<EpisodeEntity> _result = new ArrayList<EpisodeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EpisodeEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSeriesId;
            _tmpSeriesId = _cursor.getString(_cursorIndexOfSeriesId);
            final String _tmpSeasonId;
            _tmpSeasonId = _cursor.getString(_cursorIndexOfSeasonId);
            final int _tmpEpisodeNumber;
            _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            }
            final String _tmpPosterUrl;
            if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
              _tmpPosterUrl = null;
            } else {
              _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            }
            final Integer _tmpDurationSec;
            if (_cursor.isNull(_cursorIndexOfDurationSec)) {
              _tmpDurationSec = null;
            } else {
              _tmpDurationSec = _cursor.getInt(_cursorIndexOfDurationSec);
            }
            final String _tmpStreamUrl;
            _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            final String _tmpAdded;
            if (_cursor.isNull(_cursorIndexOfAdded)) {
              _tmpAdded = null;
            } else {
              _tmpAdded = _cursor.getString(_cursorIndexOfAdded);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            _item = new EpisodeEntity(_tmpId,_tmpSeriesId,_tmpSeasonId,_tmpEpisodeNumber,_tmpSeasonNumber,_tmpName,_tmpPlot,_tmpPosterUrl,_tmpDurationSec,_tmpStreamUrl,_tmpContainerExtension,_tmpAdded,_tmpRating);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getEpisodeById(final String episodeId,
      final Continuation<? super EpisodeEntity> $completion) {
    final String _sql = "SELECT * FROM episodes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, episodeId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EpisodeEntity>() {
      @Override
      @Nullable
      public EpisodeEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSeriesId = CursorUtil.getColumnIndexOrThrow(_cursor, "seriesId");
          final int _cursorIndexOfSeasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonId");
          final int _cursorIndexOfEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfSeasonNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "seasonNumber");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
          final int _cursorIndexOfDurationSec = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSec");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final EpisodeEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSeriesId;
            _tmpSeriesId = _cursor.getString(_cursorIndexOfSeriesId);
            final String _tmpSeasonId;
            _tmpSeasonId = _cursor.getString(_cursorIndexOfSeasonId);
            final int _tmpEpisodeNumber;
            _tmpEpisodeNumber = _cursor.getInt(_cursorIndexOfEpisodeNumber);
            final int _tmpSeasonNumber;
            _tmpSeasonNumber = _cursor.getInt(_cursorIndexOfSeasonNumber);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
            }
            final String _tmpPosterUrl;
            if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
              _tmpPosterUrl = null;
            } else {
              _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
            }
            final Integer _tmpDurationSec;
            if (_cursor.isNull(_cursorIndexOfDurationSec)) {
              _tmpDurationSec = null;
            } else {
              _tmpDurationSec = _cursor.getInt(_cursorIndexOfDurationSec);
            }
            final String _tmpStreamUrl;
            _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            final String _tmpAdded;
            if (_cursor.isNull(_cursorIndexOfAdded)) {
              _tmpAdded = null;
            } else {
              _tmpAdded = _cursor.getString(_cursorIndexOfAdded);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            _result = new EpisodeEntity(_tmpId,_tmpSeriesId,_tmpSeasonId,_tmpEpisodeNumber,_tmpSeasonNumber,_tmpName,_tmpPlot,_tmpPosterUrl,_tmpDurationSec,_tmpStreamUrl,_tmpContainerExtension,_tmpAdded,_tmpRating);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
