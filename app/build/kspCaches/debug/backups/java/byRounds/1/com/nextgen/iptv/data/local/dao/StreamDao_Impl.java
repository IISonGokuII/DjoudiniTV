package com.nextgen.iptv.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetPagingSource;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nextgen.iptv.data.local.entity.StreamEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
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
public final class StreamDao_Impl implements StreamDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StreamEntity> __insertionAdapterOfStreamEntity;

  private final EntityDeletionOrUpdateAdapter<StreamEntity> __deletionAdapterOfStreamEntity;

  private final EntityDeletionOrUpdateAdapter<StreamEntity> __updateAdapterOfStreamEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByProviderId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByCategoryId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public StreamDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStreamEntity = new EntityInsertionAdapter<StreamEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `streams` (`id`,`categoryId`,`providerId`,`name`,`streamUrl`,`logoUrl`,`epgChannelId`,`type`,`plot`,`cast`,`director`,`genre`,`rating`,`rating5Based`,`releaseDate`,`durationSecs`,`duration`,`backdropUrl`,`youtubeTrailer`,`added`,`containerExtension`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StreamEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getCategoryId());
        statement.bindString(3, entity.getProviderId());
        statement.bindString(4, entity.getName());
        statement.bindString(5, entity.getStreamUrl());
        if (entity.getLogoUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getLogoUrl());
        }
        if (entity.getEpgChannelId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getEpgChannelId());
        }
        statement.bindString(8, entity.getType());
        if (entity.getPlot() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPlot());
        }
        if (entity.getCast() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getCast());
        }
        if (entity.getDirector() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getDirector());
        }
        if (entity.getGenre() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getGenre());
        }
        if (entity.getRating() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getRating());
        }
        if (entity.getRating5Based() == null) {
          statement.bindNull(14);
        } else {
          statement.bindDouble(14, entity.getRating5Based());
        }
        if (entity.getReleaseDate() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getReleaseDate());
        }
        if (entity.getDurationSecs() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getDurationSecs());
        }
        if (entity.getDuration() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getDuration());
        }
        if (entity.getBackdropUrl() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getBackdropUrl());
        }
        if (entity.getYoutubeTrailer() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getYoutubeTrailer());
        }
        if (entity.getAdded() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getAdded());
        }
        if (entity.getContainerExtension() == null) {
          statement.bindNull(21);
        } else {
          statement.bindString(21, entity.getContainerExtension());
        }
      }
    };
    this.__deletionAdapterOfStreamEntity = new EntityDeletionOrUpdateAdapter<StreamEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `streams` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StreamEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfStreamEntity = new EntityDeletionOrUpdateAdapter<StreamEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `streams` SET `id` = ?,`categoryId` = ?,`providerId` = ?,`name` = ?,`streamUrl` = ?,`logoUrl` = ?,`epgChannelId` = ?,`type` = ?,`plot` = ?,`cast` = ?,`director` = ?,`genre` = ?,`rating` = ?,`rating5Based` = ?,`releaseDate` = ?,`durationSecs` = ?,`duration` = ?,`backdropUrl` = ?,`youtubeTrailer` = ?,`added` = ?,`containerExtension` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StreamEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getCategoryId());
        statement.bindString(3, entity.getProviderId());
        statement.bindString(4, entity.getName());
        statement.bindString(5, entity.getStreamUrl());
        if (entity.getLogoUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getLogoUrl());
        }
        if (entity.getEpgChannelId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getEpgChannelId());
        }
        statement.bindString(8, entity.getType());
        if (entity.getPlot() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPlot());
        }
        if (entity.getCast() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getCast());
        }
        if (entity.getDirector() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getDirector());
        }
        if (entity.getGenre() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getGenre());
        }
        if (entity.getRating() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getRating());
        }
        if (entity.getRating5Based() == null) {
          statement.bindNull(14);
        } else {
          statement.bindDouble(14, entity.getRating5Based());
        }
        if (entity.getReleaseDate() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getReleaseDate());
        }
        if (entity.getDurationSecs() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getDurationSecs());
        }
        if (entity.getDuration() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getDuration());
        }
        if (entity.getBackdropUrl() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getBackdropUrl());
        }
        if (entity.getYoutubeTrailer() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getYoutubeTrailer());
        }
        if (entity.getAdded() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getAdded());
        }
        if (entity.getContainerExtension() == null) {
          statement.bindNull(21);
        } else {
          statement.bindString(21, entity.getContainerExtension());
        }
        statement.bindString(22, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByProviderId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM streams WHERE providerId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByCategoryId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM streams WHERE categoryId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM streams";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final StreamEntity stream, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStreamEntity.insert(stream);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<StreamEntity> streams,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStreamEntity.insert(streams);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final StreamEntity stream, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfStreamEntity.handle(stream);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final StreamEntity stream, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStreamEntity.handle(stream);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByProviderId(final String providerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByProviderId.acquire();
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
          __preparedStmtOfDeleteByProviderId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByCategoryId(final String categoryId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByCategoryId.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, categoryId);
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
          __preparedStmtOfDeleteByCategoryId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
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
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<StreamEntity>> getAll() {
    final String _sql = "SELECT * FROM streams ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"streams"}, new Callable<List<StreamEntity>>() {
      @Override
      @NonNull
      public List<StreamEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
          final int _cursorIndexOfEpgChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "epgChannelId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRating5Based = CursorUtil.getColumnIndexOrThrow(_cursor, "rating5Based");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfDurationSecs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSecs");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfYoutubeTrailer = CursorUtil.getColumnIndexOrThrow(_cursor, "youtubeTrailer");
          final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final List<StreamEntity> _result = new ArrayList<StreamEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StreamEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpStreamUrl;
            _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            final String _tmpLogoUrl;
            if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
              _tmpLogoUrl = null;
            } else {
              _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
            }
            final String _tmpEpgChannelId;
            if (_cursor.isNull(_cursorIndexOfEpgChannelId)) {
              _tmpEpgChannelId = null;
            } else {
              _tmpEpgChannelId = _cursor.getString(_cursorIndexOfEpgChannelId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
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
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final Double _tmpRating5Based;
            if (_cursor.isNull(_cursorIndexOfRating5Based)) {
              _tmpRating5Based = null;
            } else {
              _tmpRating5Based = _cursor.getDouble(_cursorIndexOfRating5Based);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final Integer _tmpDurationSecs;
            if (_cursor.isNull(_cursorIndexOfDurationSecs)) {
              _tmpDurationSecs = null;
            } else {
              _tmpDurationSecs = _cursor.getInt(_cursorIndexOfDurationSecs);
            }
            final String _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpYoutubeTrailer;
            if (_cursor.isNull(_cursorIndexOfYoutubeTrailer)) {
              _tmpYoutubeTrailer = null;
            } else {
              _tmpYoutubeTrailer = _cursor.getString(_cursorIndexOfYoutubeTrailer);
            }
            final String _tmpAdded;
            if (_cursor.isNull(_cursorIndexOfAdded)) {
              _tmpAdded = null;
            } else {
              _tmpAdded = _cursor.getString(_cursorIndexOfAdded);
            }
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            _item = new StreamEntity(_tmpId,_tmpCategoryId,_tmpProviderId,_tmpName,_tmpStreamUrl,_tmpLogoUrl,_tmpEpgChannelId,_tmpType,_tmpPlot,_tmpCast,_tmpDirector,_tmpGenre,_tmpRating,_tmpRating5Based,_tmpReleaseDate,_tmpDurationSecs,_tmpDuration,_tmpBackdropUrl,_tmpYoutubeTrailer,_tmpAdded,_tmpContainerExtension);
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
  public Flow<List<StreamEntity>> getByCategoryId(final String categoryId) {
    final String _sql = "SELECT * FROM streams WHERE categoryId = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, categoryId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"streams"}, new Callable<List<StreamEntity>>() {
      @Override
      @NonNull
      public List<StreamEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
          final int _cursorIndexOfEpgChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "epgChannelId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRating5Based = CursorUtil.getColumnIndexOrThrow(_cursor, "rating5Based");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfDurationSecs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSecs");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfYoutubeTrailer = CursorUtil.getColumnIndexOrThrow(_cursor, "youtubeTrailer");
          final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final List<StreamEntity> _result = new ArrayList<StreamEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StreamEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpStreamUrl;
            _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            final String _tmpLogoUrl;
            if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
              _tmpLogoUrl = null;
            } else {
              _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
            }
            final String _tmpEpgChannelId;
            if (_cursor.isNull(_cursorIndexOfEpgChannelId)) {
              _tmpEpgChannelId = null;
            } else {
              _tmpEpgChannelId = _cursor.getString(_cursorIndexOfEpgChannelId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
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
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final Double _tmpRating5Based;
            if (_cursor.isNull(_cursorIndexOfRating5Based)) {
              _tmpRating5Based = null;
            } else {
              _tmpRating5Based = _cursor.getDouble(_cursorIndexOfRating5Based);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final Integer _tmpDurationSecs;
            if (_cursor.isNull(_cursorIndexOfDurationSecs)) {
              _tmpDurationSecs = null;
            } else {
              _tmpDurationSecs = _cursor.getInt(_cursorIndexOfDurationSecs);
            }
            final String _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpYoutubeTrailer;
            if (_cursor.isNull(_cursorIndexOfYoutubeTrailer)) {
              _tmpYoutubeTrailer = null;
            } else {
              _tmpYoutubeTrailer = _cursor.getString(_cursorIndexOfYoutubeTrailer);
            }
            final String _tmpAdded;
            if (_cursor.isNull(_cursorIndexOfAdded)) {
              _tmpAdded = null;
            } else {
              _tmpAdded = _cursor.getString(_cursorIndexOfAdded);
            }
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            _item = new StreamEntity(_tmpId,_tmpCategoryId,_tmpProviderId,_tmpName,_tmpStreamUrl,_tmpLogoUrl,_tmpEpgChannelId,_tmpType,_tmpPlot,_tmpCast,_tmpDirector,_tmpGenre,_tmpRating,_tmpRating5Based,_tmpReleaseDate,_tmpDurationSecs,_tmpDuration,_tmpBackdropUrl,_tmpYoutubeTrailer,_tmpAdded,_tmpContainerExtension);
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
  public Flow<List<StreamEntity>> getByCategoryIds(final List<String> categoryIds) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM streams WHERE categoryId IN (");
    final int _inputSize = categoryIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") ORDER BY name ASC");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : categoryIds) {
      _statement.bindString(_argIndex, _item);
      _argIndex++;
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"streams"}, new Callable<List<StreamEntity>>() {
      @Override
      @NonNull
      public List<StreamEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
          final int _cursorIndexOfEpgChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "epgChannelId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRating5Based = CursorUtil.getColumnIndexOrThrow(_cursor, "rating5Based");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfDurationSecs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSecs");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfYoutubeTrailer = CursorUtil.getColumnIndexOrThrow(_cursor, "youtubeTrailer");
          final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final List<StreamEntity> _result = new ArrayList<StreamEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StreamEntity _item_1;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpStreamUrl;
            _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            final String _tmpLogoUrl;
            if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
              _tmpLogoUrl = null;
            } else {
              _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
            }
            final String _tmpEpgChannelId;
            if (_cursor.isNull(_cursorIndexOfEpgChannelId)) {
              _tmpEpgChannelId = null;
            } else {
              _tmpEpgChannelId = _cursor.getString(_cursorIndexOfEpgChannelId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
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
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final Double _tmpRating5Based;
            if (_cursor.isNull(_cursorIndexOfRating5Based)) {
              _tmpRating5Based = null;
            } else {
              _tmpRating5Based = _cursor.getDouble(_cursorIndexOfRating5Based);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final Integer _tmpDurationSecs;
            if (_cursor.isNull(_cursorIndexOfDurationSecs)) {
              _tmpDurationSecs = null;
            } else {
              _tmpDurationSecs = _cursor.getInt(_cursorIndexOfDurationSecs);
            }
            final String _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpYoutubeTrailer;
            if (_cursor.isNull(_cursorIndexOfYoutubeTrailer)) {
              _tmpYoutubeTrailer = null;
            } else {
              _tmpYoutubeTrailer = _cursor.getString(_cursorIndexOfYoutubeTrailer);
            }
            final String _tmpAdded;
            if (_cursor.isNull(_cursorIndexOfAdded)) {
              _tmpAdded = null;
            } else {
              _tmpAdded = _cursor.getString(_cursorIndexOfAdded);
            }
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            _item_1 = new StreamEntity(_tmpId,_tmpCategoryId,_tmpProviderId,_tmpName,_tmpStreamUrl,_tmpLogoUrl,_tmpEpgChannelId,_tmpType,_tmpPlot,_tmpCast,_tmpDirector,_tmpGenre,_tmpRating,_tmpRating5Based,_tmpReleaseDate,_tmpDurationSecs,_tmpDuration,_tmpBackdropUrl,_tmpYoutubeTrailer,_tmpAdded,_tmpContainerExtension);
            _result.add(_item_1);
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
  public Flow<List<StreamEntity>> getByProviderId(final String providerId) {
    final String _sql = "SELECT * FROM streams WHERE providerId = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, providerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"streams"}, new Callable<List<StreamEntity>>() {
      @Override
      @NonNull
      public List<StreamEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
          final int _cursorIndexOfEpgChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "epgChannelId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRating5Based = CursorUtil.getColumnIndexOrThrow(_cursor, "rating5Based");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfDurationSecs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSecs");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfYoutubeTrailer = CursorUtil.getColumnIndexOrThrow(_cursor, "youtubeTrailer");
          final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final List<StreamEntity> _result = new ArrayList<StreamEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StreamEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpStreamUrl;
            _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            final String _tmpLogoUrl;
            if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
              _tmpLogoUrl = null;
            } else {
              _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
            }
            final String _tmpEpgChannelId;
            if (_cursor.isNull(_cursorIndexOfEpgChannelId)) {
              _tmpEpgChannelId = null;
            } else {
              _tmpEpgChannelId = _cursor.getString(_cursorIndexOfEpgChannelId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
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
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final Double _tmpRating5Based;
            if (_cursor.isNull(_cursorIndexOfRating5Based)) {
              _tmpRating5Based = null;
            } else {
              _tmpRating5Based = _cursor.getDouble(_cursorIndexOfRating5Based);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final Integer _tmpDurationSecs;
            if (_cursor.isNull(_cursorIndexOfDurationSecs)) {
              _tmpDurationSecs = null;
            } else {
              _tmpDurationSecs = _cursor.getInt(_cursorIndexOfDurationSecs);
            }
            final String _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpYoutubeTrailer;
            if (_cursor.isNull(_cursorIndexOfYoutubeTrailer)) {
              _tmpYoutubeTrailer = null;
            } else {
              _tmpYoutubeTrailer = _cursor.getString(_cursorIndexOfYoutubeTrailer);
            }
            final String _tmpAdded;
            if (_cursor.isNull(_cursorIndexOfAdded)) {
              _tmpAdded = null;
            } else {
              _tmpAdded = _cursor.getString(_cursorIndexOfAdded);
            }
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            _item = new StreamEntity(_tmpId,_tmpCategoryId,_tmpProviderId,_tmpName,_tmpStreamUrl,_tmpLogoUrl,_tmpEpgChannelId,_tmpType,_tmpPlot,_tmpCast,_tmpDirector,_tmpGenre,_tmpRating,_tmpRating5Based,_tmpReleaseDate,_tmpDurationSecs,_tmpDuration,_tmpBackdropUrl,_tmpYoutubeTrailer,_tmpAdded,_tmpContainerExtension);
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
  public Flow<List<StreamEntity>> getByType(final String type) {
    final String _sql = "SELECT * FROM streams WHERE type = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, type);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"streams"}, new Callable<List<StreamEntity>>() {
      @Override
      @NonNull
      public List<StreamEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
          final int _cursorIndexOfEpgChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "epgChannelId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRating5Based = CursorUtil.getColumnIndexOrThrow(_cursor, "rating5Based");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfDurationSecs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSecs");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfYoutubeTrailer = CursorUtil.getColumnIndexOrThrow(_cursor, "youtubeTrailer");
          final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final List<StreamEntity> _result = new ArrayList<StreamEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StreamEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpStreamUrl;
            _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            final String _tmpLogoUrl;
            if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
              _tmpLogoUrl = null;
            } else {
              _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
            }
            final String _tmpEpgChannelId;
            if (_cursor.isNull(_cursorIndexOfEpgChannelId)) {
              _tmpEpgChannelId = null;
            } else {
              _tmpEpgChannelId = _cursor.getString(_cursorIndexOfEpgChannelId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
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
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final Double _tmpRating5Based;
            if (_cursor.isNull(_cursorIndexOfRating5Based)) {
              _tmpRating5Based = null;
            } else {
              _tmpRating5Based = _cursor.getDouble(_cursorIndexOfRating5Based);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final Integer _tmpDurationSecs;
            if (_cursor.isNull(_cursorIndexOfDurationSecs)) {
              _tmpDurationSecs = null;
            } else {
              _tmpDurationSecs = _cursor.getInt(_cursorIndexOfDurationSecs);
            }
            final String _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpYoutubeTrailer;
            if (_cursor.isNull(_cursorIndexOfYoutubeTrailer)) {
              _tmpYoutubeTrailer = null;
            } else {
              _tmpYoutubeTrailer = _cursor.getString(_cursorIndexOfYoutubeTrailer);
            }
            final String _tmpAdded;
            if (_cursor.isNull(_cursorIndexOfAdded)) {
              _tmpAdded = null;
            } else {
              _tmpAdded = _cursor.getString(_cursorIndexOfAdded);
            }
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            _item = new StreamEntity(_tmpId,_tmpCategoryId,_tmpProviderId,_tmpName,_tmpStreamUrl,_tmpLogoUrl,_tmpEpgChannelId,_tmpType,_tmpPlot,_tmpCast,_tmpDirector,_tmpGenre,_tmpRating,_tmpRating5Based,_tmpReleaseDate,_tmpDurationSecs,_tmpDuration,_tmpBackdropUrl,_tmpYoutubeTrailer,_tmpAdded,_tmpContainerExtension);
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
  public Flow<List<StreamEntity>> getByTypeAndCategories(final String type,
      final List<String> categoryIds) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM streams WHERE type = ");
    _stringBuilder.append("?");
    _stringBuilder.append(" AND categoryId IN (");
    final int _inputSize = categoryIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") ORDER BY name ASC");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 1 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    _statement.bindString(_argIndex, type);
    _argIndex = 2;
    for (String _item : categoryIds) {
      _statement.bindString(_argIndex, _item);
      _argIndex++;
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"streams"}, new Callable<List<StreamEntity>>() {
      @Override
      @NonNull
      public List<StreamEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
          final int _cursorIndexOfEpgChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "epgChannelId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRating5Based = CursorUtil.getColumnIndexOrThrow(_cursor, "rating5Based");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfDurationSecs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSecs");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfYoutubeTrailer = CursorUtil.getColumnIndexOrThrow(_cursor, "youtubeTrailer");
          final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final List<StreamEntity> _result = new ArrayList<StreamEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StreamEntity _item_1;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpStreamUrl;
            _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            final String _tmpLogoUrl;
            if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
              _tmpLogoUrl = null;
            } else {
              _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
            }
            final String _tmpEpgChannelId;
            if (_cursor.isNull(_cursorIndexOfEpgChannelId)) {
              _tmpEpgChannelId = null;
            } else {
              _tmpEpgChannelId = _cursor.getString(_cursorIndexOfEpgChannelId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
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
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final Double _tmpRating5Based;
            if (_cursor.isNull(_cursorIndexOfRating5Based)) {
              _tmpRating5Based = null;
            } else {
              _tmpRating5Based = _cursor.getDouble(_cursorIndexOfRating5Based);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final Integer _tmpDurationSecs;
            if (_cursor.isNull(_cursorIndexOfDurationSecs)) {
              _tmpDurationSecs = null;
            } else {
              _tmpDurationSecs = _cursor.getInt(_cursorIndexOfDurationSecs);
            }
            final String _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpYoutubeTrailer;
            if (_cursor.isNull(_cursorIndexOfYoutubeTrailer)) {
              _tmpYoutubeTrailer = null;
            } else {
              _tmpYoutubeTrailer = _cursor.getString(_cursorIndexOfYoutubeTrailer);
            }
            final String _tmpAdded;
            if (_cursor.isNull(_cursorIndexOfAdded)) {
              _tmpAdded = null;
            } else {
              _tmpAdded = _cursor.getString(_cursorIndexOfAdded);
            }
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            _item_1 = new StreamEntity(_tmpId,_tmpCategoryId,_tmpProviderId,_tmpName,_tmpStreamUrl,_tmpLogoUrl,_tmpEpgChannelId,_tmpType,_tmpPlot,_tmpCast,_tmpDirector,_tmpGenre,_tmpRating,_tmpRating5Based,_tmpReleaseDate,_tmpDurationSecs,_tmpDuration,_tmpBackdropUrl,_tmpYoutubeTrailer,_tmpAdded,_tmpContainerExtension);
            _result.add(_item_1);
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
  public Object getById(final String id, final Continuation<? super StreamEntity> $completion) {
    final String _sql = "SELECT * FROM streams WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StreamEntity>() {
      @Override
      @Nullable
      public StreamEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
          final int _cursorIndexOfEpgChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "epgChannelId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(_cursor, "plot");
          final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(_cursor, "cast");
          final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(_cursor, "director");
          final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(_cursor, "genre");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfRating5Based = CursorUtil.getColumnIndexOrThrow(_cursor, "rating5Based");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfDurationSecs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSecs");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropUrl");
          final int _cursorIndexOfYoutubeTrailer = CursorUtil.getColumnIndexOrThrow(_cursor, "youtubeTrailer");
          final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "added");
          final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(_cursor, "containerExtension");
          final StreamEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpStreamUrl;
            _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            final String _tmpLogoUrl;
            if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
              _tmpLogoUrl = null;
            } else {
              _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
            }
            final String _tmpEpgChannelId;
            if (_cursor.isNull(_cursorIndexOfEpgChannelId)) {
              _tmpEpgChannelId = null;
            } else {
              _tmpEpgChannelId = _cursor.getString(_cursorIndexOfEpgChannelId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpPlot;
            if (_cursor.isNull(_cursorIndexOfPlot)) {
              _tmpPlot = null;
            } else {
              _tmpPlot = _cursor.getString(_cursorIndexOfPlot);
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
            final String _tmpGenre;
            if (_cursor.isNull(_cursorIndexOfGenre)) {
              _tmpGenre = null;
            } else {
              _tmpGenre = _cursor.getString(_cursorIndexOfGenre);
            }
            final String _tmpRating;
            if (_cursor.isNull(_cursorIndexOfRating)) {
              _tmpRating = null;
            } else {
              _tmpRating = _cursor.getString(_cursorIndexOfRating);
            }
            final Double _tmpRating5Based;
            if (_cursor.isNull(_cursorIndexOfRating5Based)) {
              _tmpRating5Based = null;
            } else {
              _tmpRating5Based = _cursor.getDouble(_cursorIndexOfRating5Based);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final Integer _tmpDurationSecs;
            if (_cursor.isNull(_cursorIndexOfDurationSecs)) {
              _tmpDurationSecs = null;
            } else {
              _tmpDurationSecs = _cursor.getInt(_cursorIndexOfDurationSecs);
            }
            final String _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            }
            final String _tmpBackdropUrl;
            if (_cursor.isNull(_cursorIndexOfBackdropUrl)) {
              _tmpBackdropUrl = null;
            } else {
              _tmpBackdropUrl = _cursor.getString(_cursorIndexOfBackdropUrl);
            }
            final String _tmpYoutubeTrailer;
            if (_cursor.isNull(_cursorIndexOfYoutubeTrailer)) {
              _tmpYoutubeTrailer = null;
            } else {
              _tmpYoutubeTrailer = _cursor.getString(_cursorIndexOfYoutubeTrailer);
            }
            final String _tmpAdded;
            if (_cursor.isNull(_cursorIndexOfAdded)) {
              _tmpAdded = null;
            } else {
              _tmpAdded = _cursor.getString(_cursorIndexOfAdded);
            }
            final String _tmpContainerExtension;
            if (_cursor.isNull(_cursorIndexOfContainerExtension)) {
              _tmpContainerExtension = null;
            } else {
              _tmpContainerExtension = _cursor.getString(_cursorIndexOfContainerExtension);
            }
            _result = new StreamEntity(_tmpId,_tmpCategoryId,_tmpProviderId,_tmpName,_tmpStreamUrl,_tmpLogoUrl,_tmpEpgChannelId,_tmpType,_tmpPlot,_tmpCast,_tmpDirector,_tmpGenre,_tmpRating,_tmpRating5Based,_tmpReleaseDate,_tmpDurationSecs,_tmpDuration,_tmpBackdropUrl,_tmpYoutubeTrailer,_tmpAdded,_tmpContainerExtension);
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
  public PagingSource<Integer, StreamEntity> getByCategoryPaged(final String categoryId) {
    final String _sql = "SELECT * FROM streams WHERE categoryId = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, categoryId);
    return new LimitOffsetPagingSource<StreamEntity>(_statement, __db, "streams") {
      @Override
      @NonNull
      protected List<StreamEntity> convertRows(@NonNull final Cursor cursor) {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
        final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(cursor, "categoryId");
        final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(cursor, "providerId");
        final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(cursor, "name");
        final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(cursor, "streamUrl");
        final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(cursor, "logoUrl");
        final int _cursorIndexOfEpgChannelId = CursorUtil.getColumnIndexOrThrow(cursor, "epgChannelId");
        final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(cursor, "type");
        final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(cursor, "plot");
        final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(cursor, "cast");
        final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(cursor, "director");
        final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(cursor, "genre");
        final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(cursor, "rating");
        final int _cursorIndexOfRating5Based = CursorUtil.getColumnIndexOrThrow(cursor, "rating5Based");
        final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(cursor, "releaseDate");
        final int _cursorIndexOfDurationSecs = CursorUtil.getColumnIndexOrThrow(cursor, "durationSecs");
        final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(cursor, "duration");
        final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(cursor, "backdropUrl");
        final int _cursorIndexOfYoutubeTrailer = CursorUtil.getColumnIndexOrThrow(cursor, "youtubeTrailer");
        final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(cursor, "added");
        final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(cursor, "containerExtension");
        final List<StreamEntity> _result = new ArrayList<StreamEntity>(cursor.getCount());
        while (cursor.moveToNext()) {
          final StreamEntity _item;
          final String _tmpId;
          _tmpId = cursor.getString(_cursorIndexOfId);
          final String _tmpCategoryId;
          _tmpCategoryId = cursor.getString(_cursorIndexOfCategoryId);
          final String _tmpProviderId;
          _tmpProviderId = cursor.getString(_cursorIndexOfProviderId);
          final String _tmpName;
          _tmpName = cursor.getString(_cursorIndexOfName);
          final String _tmpStreamUrl;
          _tmpStreamUrl = cursor.getString(_cursorIndexOfStreamUrl);
          final String _tmpLogoUrl;
          if (cursor.isNull(_cursorIndexOfLogoUrl)) {
            _tmpLogoUrl = null;
          } else {
            _tmpLogoUrl = cursor.getString(_cursorIndexOfLogoUrl);
          }
          final String _tmpEpgChannelId;
          if (cursor.isNull(_cursorIndexOfEpgChannelId)) {
            _tmpEpgChannelId = null;
          } else {
            _tmpEpgChannelId = cursor.getString(_cursorIndexOfEpgChannelId);
          }
          final String _tmpType;
          _tmpType = cursor.getString(_cursorIndexOfType);
          final String _tmpPlot;
          if (cursor.isNull(_cursorIndexOfPlot)) {
            _tmpPlot = null;
          } else {
            _tmpPlot = cursor.getString(_cursorIndexOfPlot);
          }
          final String _tmpCast;
          if (cursor.isNull(_cursorIndexOfCast)) {
            _tmpCast = null;
          } else {
            _tmpCast = cursor.getString(_cursorIndexOfCast);
          }
          final String _tmpDirector;
          if (cursor.isNull(_cursorIndexOfDirector)) {
            _tmpDirector = null;
          } else {
            _tmpDirector = cursor.getString(_cursorIndexOfDirector);
          }
          final String _tmpGenre;
          if (cursor.isNull(_cursorIndexOfGenre)) {
            _tmpGenre = null;
          } else {
            _tmpGenre = cursor.getString(_cursorIndexOfGenre);
          }
          final String _tmpRating;
          if (cursor.isNull(_cursorIndexOfRating)) {
            _tmpRating = null;
          } else {
            _tmpRating = cursor.getString(_cursorIndexOfRating);
          }
          final Double _tmpRating5Based;
          if (cursor.isNull(_cursorIndexOfRating5Based)) {
            _tmpRating5Based = null;
          } else {
            _tmpRating5Based = cursor.getDouble(_cursorIndexOfRating5Based);
          }
          final String _tmpReleaseDate;
          if (cursor.isNull(_cursorIndexOfReleaseDate)) {
            _tmpReleaseDate = null;
          } else {
            _tmpReleaseDate = cursor.getString(_cursorIndexOfReleaseDate);
          }
          final Integer _tmpDurationSecs;
          if (cursor.isNull(_cursorIndexOfDurationSecs)) {
            _tmpDurationSecs = null;
          } else {
            _tmpDurationSecs = cursor.getInt(_cursorIndexOfDurationSecs);
          }
          final String _tmpDuration;
          if (cursor.isNull(_cursorIndexOfDuration)) {
            _tmpDuration = null;
          } else {
            _tmpDuration = cursor.getString(_cursorIndexOfDuration);
          }
          final String _tmpBackdropUrl;
          if (cursor.isNull(_cursorIndexOfBackdropUrl)) {
            _tmpBackdropUrl = null;
          } else {
            _tmpBackdropUrl = cursor.getString(_cursorIndexOfBackdropUrl);
          }
          final String _tmpYoutubeTrailer;
          if (cursor.isNull(_cursorIndexOfYoutubeTrailer)) {
            _tmpYoutubeTrailer = null;
          } else {
            _tmpYoutubeTrailer = cursor.getString(_cursorIndexOfYoutubeTrailer);
          }
          final String _tmpAdded;
          if (cursor.isNull(_cursorIndexOfAdded)) {
            _tmpAdded = null;
          } else {
            _tmpAdded = cursor.getString(_cursorIndexOfAdded);
          }
          final String _tmpContainerExtension;
          if (cursor.isNull(_cursorIndexOfContainerExtension)) {
            _tmpContainerExtension = null;
          } else {
            _tmpContainerExtension = cursor.getString(_cursorIndexOfContainerExtension);
          }
          _item = new StreamEntity(_tmpId,_tmpCategoryId,_tmpProviderId,_tmpName,_tmpStreamUrl,_tmpLogoUrl,_tmpEpgChannelId,_tmpType,_tmpPlot,_tmpCast,_tmpDirector,_tmpGenre,_tmpRating,_tmpRating5Based,_tmpReleaseDate,_tmpDurationSecs,_tmpDuration,_tmpBackdropUrl,_tmpYoutubeTrailer,_tmpAdded,_tmpContainerExtension);
          _result.add(_item);
        }
        return _result;
      }
    };
  }

  @Override
  public PagingSource<Integer, StreamEntity> getByTypePaged(final String type) {
    final String _sql = "SELECT * FROM streams WHERE type = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, type);
    return new LimitOffsetPagingSource<StreamEntity>(_statement, __db, "streams") {
      @Override
      @NonNull
      protected List<StreamEntity> convertRows(@NonNull final Cursor cursor) {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
        final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(cursor, "categoryId");
        final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(cursor, "providerId");
        final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(cursor, "name");
        final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(cursor, "streamUrl");
        final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(cursor, "logoUrl");
        final int _cursorIndexOfEpgChannelId = CursorUtil.getColumnIndexOrThrow(cursor, "epgChannelId");
        final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(cursor, "type");
        final int _cursorIndexOfPlot = CursorUtil.getColumnIndexOrThrow(cursor, "plot");
        final int _cursorIndexOfCast = CursorUtil.getColumnIndexOrThrow(cursor, "cast");
        final int _cursorIndexOfDirector = CursorUtil.getColumnIndexOrThrow(cursor, "director");
        final int _cursorIndexOfGenre = CursorUtil.getColumnIndexOrThrow(cursor, "genre");
        final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(cursor, "rating");
        final int _cursorIndexOfRating5Based = CursorUtil.getColumnIndexOrThrow(cursor, "rating5Based");
        final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(cursor, "releaseDate");
        final int _cursorIndexOfDurationSecs = CursorUtil.getColumnIndexOrThrow(cursor, "durationSecs");
        final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(cursor, "duration");
        final int _cursorIndexOfBackdropUrl = CursorUtil.getColumnIndexOrThrow(cursor, "backdropUrl");
        final int _cursorIndexOfYoutubeTrailer = CursorUtil.getColumnIndexOrThrow(cursor, "youtubeTrailer");
        final int _cursorIndexOfAdded = CursorUtil.getColumnIndexOrThrow(cursor, "added");
        final int _cursorIndexOfContainerExtension = CursorUtil.getColumnIndexOrThrow(cursor, "containerExtension");
        final List<StreamEntity> _result = new ArrayList<StreamEntity>(cursor.getCount());
        while (cursor.moveToNext()) {
          final StreamEntity _item;
          final String _tmpId;
          _tmpId = cursor.getString(_cursorIndexOfId);
          final String _tmpCategoryId;
          _tmpCategoryId = cursor.getString(_cursorIndexOfCategoryId);
          final String _tmpProviderId;
          _tmpProviderId = cursor.getString(_cursorIndexOfProviderId);
          final String _tmpName;
          _tmpName = cursor.getString(_cursorIndexOfName);
          final String _tmpStreamUrl;
          _tmpStreamUrl = cursor.getString(_cursorIndexOfStreamUrl);
          final String _tmpLogoUrl;
          if (cursor.isNull(_cursorIndexOfLogoUrl)) {
            _tmpLogoUrl = null;
          } else {
            _tmpLogoUrl = cursor.getString(_cursorIndexOfLogoUrl);
          }
          final String _tmpEpgChannelId;
          if (cursor.isNull(_cursorIndexOfEpgChannelId)) {
            _tmpEpgChannelId = null;
          } else {
            _tmpEpgChannelId = cursor.getString(_cursorIndexOfEpgChannelId);
          }
          final String _tmpType;
          _tmpType = cursor.getString(_cursorIndexOfType);
          final String _tmpPlot;
          if (cursor.isNull(_cursorIndexOfPlot)) {
            _tmpPlot = null;
          } else {
            _tmpPlot = cursor.getString(_cursorIndexOfPlot);
          }
          final String _tmpCast;
          if (cursor.isNull(_cursorIndexOfCast)) {
            _tmpCast = null;
          } else {
            _tmpCast = cursor.getString(_cursorIndexOfCast);
          }
          final String _tmpDirector;
          if (cursor.isNull(_cursorIndexOfDirector)) {
            _tmpDirector = null;
          } else {
            _tmpDirector = cursor.getString(_cursorIndexOfDirector);
          }
          final String _tmpGenre;
          if (cursor.isNull(_cursorIndexOfGenre)) {
            _tmpGenre = null;
          } else {
            _tmpGenre = cursor.getString(_cursorIndexOfGenre);
          }
          final String _tmpRating;
          if (cursor.isNull(_cursorIndexOfRating)) {
            _tmpRating = null;
          } else {
            _tmpRating = cursor.getString(_cursorIndexOfRating);
          }
          final Double _tmpRating5Based;
          if (cursor.isNull(_cursorIndexOfRating5Based)) {
            _tmpRating5Based = null;
          } else {
            _tmpRating5Based = cursor.getDouble(_cursorIndexOfRating5Based);
          }
          final String _tmpReleaseDate;
          if (cursor.isNull(_cursorIndexOfReleaseDate)) {
            _tmpReleaseDate = null;
          } else {
            _tmpReleaseDate = cursor.getString(_cursorIndexOfReleaseDate);
          }
          final Integer _tmpDurationSecs;
          if (cursor.isNull(_cursorIndexOfDurationSecs)) {
            _tmpDurationSecs = null;
          } else {
            _tmpDurationSecs = cursor.getInt(_cursorIndexOfDurationSecs);
          }
          final String _tmpDuration;
          if (cursor.isNull(_cursorIndexOfDuration)) {
            _tmpDuration = null;
          } else {
            _tmpDuration = cursor.getString(_cursorIndexOfDuration);
          }
          final String _tmpBackdropUrl;
          if (cursor.isNull(_cursorIndexOfBackdropUrl)) {
            _tmpBackdropUrl = null;
          } else {
            _tmpBackdropUrl = cursor.getString(_cursorIndexOfBackdropUrl);
          }
          final String _tmpYoutubeTrailer;
          if (cursor.isNull(_cursorIndexOfYoutubeTrailer)) {
            _tmpYoutubeTrailer = null;
          } else {
            _tmpYoutubeTrailer = cursor.getString(_cursorIndexOfYoutubeTrailer);
          }
          final String _tmpAdded;
          if (cursor.isNull(_cursorIndexOfAdded)) {
            _tmpAdded = null;
          } else {
            _tmpAdded = cursor.getString(_cursorIndexOfAdded);
          }
          final String _tmpContainerExtension;
          if (cursor.isNull(_cursorIndexOfContainerExtension)) {
            _tmpContainerExtension = null;
          } else {
            _tmpContainerExtension = cursor.getString(_cursorIndexOfContainerExtension);
          }
          _item = new StreamEntity(_tmpId,_tmpCategoryId,_tmpProviderId,_tmpName,_tmpStreamUrl,_tmpLogoUrl,_tmpEpgChannelId,_tmpType,_tmpPlot,_tmpCast,_tmpDirector,_tmpGenre,_tmpRating,_tmpRating5Based,_tmpReleaseDate,_tmpDurationSecs,_tmpDuration,_tmpBackdropUrl,_tmpYoutubeTrailer,_tmpAdded,_tmpContainerExtension);
          _result.add(_item);
        }
        return _result;
      }
    };
  }

  @Override
  public Object getCountByProvider(final String providerId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM streams WHERE providerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, providerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
