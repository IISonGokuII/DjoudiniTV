package com.nextgen.iptv.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nextgen.iptv.data.local.entity.VodProgressEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class VodProgressDao_Impl implements VodProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VodProgressEntity> __insertionAdapterOfVodProgressEntity;

  private final EntityDeletionOrUpdateAdapter<VodProgressEntity> __deletionAdapterOfVodProgressEntity;

  private final EntityDeletionOrUpdateAdapter<VodProgressEntity> __updateAdapterOfVodProgressEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByStreamId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public VodProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVodProgressEntity = new EntityInsertionAdapter<VodProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vod_progress` (`streamId`,`progressMs`,`durationMs`,`lastWatched`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VodProgressEntity entity) {
        statement.bindString(1, entity.getStreamId());
        statement.bindLong(2, entity.getProgressMs());
        statement.bindLong(3, entity.getDurationMs());
        statement.bindLong(4, entity.getLastWatched());
      }
    };
    this.__deletionAdapterOfVodProgressEntity = new EntityDeletionOrUpdateAdapter<VodProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `vod_progress` WHERE `streamId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VodProgressEntity entity) {
        statement.bindString(1, entity.getStreamId());
      }
    };
    this.__updateAdapterOfVodProgressEntity = new EntityDeletionOrUpdateAdapter<VodProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `vod_progress` SET `streamId` = ?,`progressMs` = ?,`durationMs` = ?,`lastWatched` = ? WHERE `streamId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VodProgressEntity entity) {
        statement.bindString(1, entity.getStreamId());
        statement.bindLong(2, entity.getProgressMs());
        statement.bindLong(3, entity.getDurationMs());
        statement.bindLong(4, entity.getLastWatched());
        statement.bindString(5, entity.getStreamId());
      }
    };
    this.__preparedStmtOfDeleteByStreamId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vod_progress WHERE streamId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vod_progress";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final VodProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVodProgressEntity.insert(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final VodProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfVodProgressEntity.handle(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final VodProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfVodProgressEntity.handle(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByStreamId(final String streamId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByStreamId.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, streamId);
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
          __preparedStmtOfDeleteByStreamId.release(_stmt);
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
  public Flow<List<VodProgressEntity>> getAll() {
    final String _sql = "SELECT * FROM vod_progress ORDER BY lastWatched DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vod_progress"}, new Callable<List<VodProgressEntity>>() {
      @Override
      @NonNull
      public List<VodProgressEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfStreamId = CursorUtil.getColumnIndexOrThrow(_cursor, "streamId");
          final int _cursorIndexOfProgressMs = CursorUtil.getColumnIndexOrThrow(_cursor, "progressMs");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfLastWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "lastWatched");
          final List<VodProgressEntity> _result = new ArrayList<VodProgressEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VodProgressEntity _item;
            final String _tmpStreamId;
            _tmpStreamId = _cursor.getString(_cursorIndexOfStreamId);
            final long _tmpProgressMs;
            _tmpProgressMs = _cursor.getLong(_cursorIndexOfProgressMs);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpLastWatched;
            _tmpLastWatched = _cursor.getLong(_cursorIndexOfLastWatched);
            _item = new VodProgressEntity(_tmpStreamId,_tmpProgressMs,_tmpDurationMs,_tmpLastWatched);
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
  public Object getByStreamId(final String streamId,
      final Continuation<? super VodProgressEntity> $completion) {
    final String _sql = "SELECT * FROM vod_progress WHERE streamId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, streamId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VodProgressEntity>() {
      @Override
      @Nullable
      public VodProgressEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfStreamId = CursorUtil.getColumnIndexOrThrow(_cursor, "streamId");
          final int _cursorIndexOfProgressMs = CursorUtil.getColumnIndexOrThrow(_cursor, "progressMs");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfLastWatched = CursorUtil.getColumnIndexOrThrow(_cursor, "lastWatched");
          final VodProgressEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpStreamId;
            _tmpStreamId = _cursor.getString(_cursorIndexOfStreamId);
            final long _tmpProgressMs;
            _tmpProgressMs = _cursor.getLong(_cursorIndexOfProgressMs);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpLastWatched;
            _tmpLastWatched = _cursor.getLong(_cursorIndexOfLastWatched);
            _result = new VodProgressEntity(_tmpStreamId,_tmpProgressMs,_tmpDurationMs,_tmpLastWatched);
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
  public Object getProgress(final String streamId, final Continuation<? super Long> $completion) {
    final String _sql = "SELECT progressMs FROM vod_progress WHERE streamId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, streamId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getLong(0);
            }
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
