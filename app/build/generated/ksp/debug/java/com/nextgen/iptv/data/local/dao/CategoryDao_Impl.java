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
import com.nextgen.iptv.data.local.entity.CategoryEntity;
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
public final class CategoryDao_Impl implements CategoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CategoryEntity> __insertionAdapterOfCategoryEntity;

  private final EntityDeletionOrUpdateAdapter<CategoryEntity> __deletionAdapterOfCategoryEntity;

  private final EntityDeletionOrUpdateAdapter<CategoryEntity> __updateAdapterOfCategoryEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByProviderId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CategoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCategoryEntity = new EntityInsertionAdapter<CategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `categories` (`id`,`providerId`,`name`,`type`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CategoryEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProviderId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getType());
      }
    };
    this.__deletionAdapterOfCategoryEntity = new EntityDeletionOrUpdateAdapter<CategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `categories` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CategoryEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfCategoryEntity = new EntityDeletionOrUpdateAdapter<CategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `categories` SET `id` = ?,`providerId` = ?,`name` = ?,`type` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CategoryEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProviderId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getType());
        statement.bindString(5, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByProviderId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM categories WHERE providerId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM categories";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final CategoryEntity category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCategoryEntity.insert(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<CategoryEntity> categories,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCategoryEntity.insert(categories);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final CategoryEntity category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCategoryEntity.handle(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final CategoryEntity category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCategoryEntity.handle(category);
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
  public Flow<List<CategoryEntity>> getAll() {
    final String _sql = "SELECT * FROM categories ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"categories"}, new Callable<List<CategoryEntity>>() {
      @Override
      @NonNull
      public List<CategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<CategoryEntity> _result = new ArrayList<CategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new CategoryEntity(_tmpId,_tmpProviderId,_tmpName,_tmpType);
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
  public Flow<List<CategoryEntity>> getByProviderId(final String providerId) {
    final String _sql = "SELECT * FROM categories WHERE providerId = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, providerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"categories"}, new Callable<List<CategoryEntity>>() {
      @Override
      @NonNull
      public List<CategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<CategoryEntity> _result = new ArrayList<CategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new CategoryEntity(_tmpId,_tmpProviderId,_tmpName,_tmpType);
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
  public Flow<List<CategoryEntity>> getByProviderAndType(final String providerId,
      final String type) {
    final String _sql = "SELECT * FROM categories WHERE providerId = ? AND type = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, providerId);
    _argIndex = 2;
    _statement.bindString(_argIndex, type);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"categories"}, new Callable<List<CategoryEntity>>() {
      @Override
      @NonNull
      public List<CategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<CategoryEntity> _result = new ArrayList<CategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new CategoryEntity(_tmpId,_tmpProviderId,_tmpName,_tmpType);
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
  public Object getById(final String id, final Continuation<? super CategoryEntity> $completion) {
    final String _sql = "SELECT * FROM categories WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CategoryEntity>() {
      @Override
      @Nullable
      public CategoryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProviderId = CursorUtil.getColumnIndexOrThrow(_cursor, "providerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final CategoryEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProviderId;
            _tmpProviderId = _cursor.getString(_cursorIndexOfProviderId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _result = new CategoryEntity(_tmpId,_tmpProviderId,_tmpName,_tmpType);
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
