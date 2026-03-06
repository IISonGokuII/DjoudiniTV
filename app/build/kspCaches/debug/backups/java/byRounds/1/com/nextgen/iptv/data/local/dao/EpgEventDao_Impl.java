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
import com.nextgen.iptv.data.local.entity.EpgEventEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class EpgEventDao_Impl implements EpgEventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EpgEventEntity> __insertionAdapterOfEpgEventEntity;

  private final EntityDeletionOrUpdateAdapter<EpgEventEntity> __deletionAdapterOfEpgEventEntity;

  private final EntityDeletionOrUpdateAdapter<EpgEventEntity> __updateAdapterOfEpgEventEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByChannelId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldEvents;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public EpgEventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEpgEventEntity = new EntityInsertionAdapter<EpgEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `epg_events` (`id`,`channelId`,`title`,`description`,`startTime`,`endTime`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EpgEventEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getChannelId());
        statement.bindString(3, entity.getTitle());
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        statement.bindLong(5, entity.getStartTime());
        statement.bindLong(6, entity.getEndTime());
      }
    };
    this.__deletionAdapterOfEpgEventEntity = new EntityDeletionOrUpdateAdapter<EpgEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `epg_events` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EpgEventEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfEpgEventEntity = new EntityDeletionOrUpdateAdapter<EpgEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `epg_events` SET `id` = ?,`channelId` = ?,`title` = ?,`description` = ?,`startTime` = ?,`endTime` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EpgEventEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getChannelId());
        statement.bindString(3, entity.getTitle());
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        statement.bindLong(5, entity.getStartTime());
        statement.bindLong(6, entity.getEndTime());
        statement.bindString(7, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByChannelId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM epg_events WHERE channelId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldEvents = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM epg_events WHERE endTime < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM epg_events";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final EpgEventEntity event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEpgEventEntity.insert(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<EpgEventEntity> events,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEpgEventEntity.insert(events);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final EpgEventEntity event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfEpgEventEntity.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final EpgEventEntity event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfEpgEventEntity.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByChannelId(final String channelId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByChannelId.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, channelId);
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
          __preparedStmtOfDeleteByChannelId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldEvents(final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldEvents.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
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
          __preparedStmtOfDeleteOldEvents.release(_stmt);
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
  public Flow<List<EpgEventEntity>> getByChannelId(final String channelId) {
    final String _sql = "SELECT * FROM epg_events WHERE channelId = ? ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, channelId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"epg_events"}, new Callable<List<EpgEventEntity>>() {
      @Override
      @NonNull
      public List<EpgEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "channelId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final List<EpgEventEntity> _result = new ArrayList<EpgEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EpgEventEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpChannelId;
            _tmpChannelId = _cursor.getString(_cursorIndexOfChannelId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            _item = new EpgEventEntity(_tmpId,_tmpChannelId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime);
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
  public Object getCurrentEvent(final String channelId, final long currentTime,
      final Continuation<? super EpgEventEntity> $completion) {
    final String _sql = "SELECT * FROM epg_events WHERE channelId = ? AND startTime <= ? AND endTime >= ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, channelId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, currentTime);
    _argIndex = 3;
    _statement.bindLong(_argIndex, currentTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EpgEventEntity>() {
      @Override
      @Nullable
      public EpgEventEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "channelId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final EpgEventEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpChannelId;
            _tmpChannelId = _cursor.getString(_cursorIndexOfChannelId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            _result = new EpgEventEntity(_tmpId,_tmpChannelId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime);
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
  public Object getUpcomingEvents(final String channelId, final long currentTime, final int limit,
      final Continuation<? super List<EpgEventEntity>> $completion) {
    final String _sql = "SELECT * FROM epg_events WHERE channelId = ? AND startTime > ? ORDER BY startTime ASC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, channelId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, currentTime);
    _argIndex = 3;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EpgEventEntity>>() {
      @Override
      @NonNull
      public List<EpgEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChannelId = CursorUtil.getColumnIndexOrThrow(_cursor, "channelId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final List<EpgEventEntity> _result = new ArrayList<EpgEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EpgEventEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpChannelId;
            _tmpChannelId = _cursor.getString(_cursorIndexOfChannelId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            _item = new EpgEventEntity(_tmpId,_tmpChannelId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime);
            _result.add(_item);
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
  public Object addEvents(final List<EpgEventEntity> events,
      final Continuation<? super Unit> $completion) {
    return EpgEventDao.DefaultImpls.addEvents(EpgEventDao_Impl.this, events, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
