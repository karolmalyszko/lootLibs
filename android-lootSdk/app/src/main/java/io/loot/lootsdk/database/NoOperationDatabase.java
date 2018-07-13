package io.loot.lootsdk.database;

import android.database.Cursor;
import android.database.SQLException;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

public class NoOperationDatabase implements Database {
    @Override
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return new NoOperationCursor();
    }

    @Override
    public void execSQL(String sql) throws SQLException {

    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void endTransaction() {

    }

    @Override
    public boolean inTransaction() {
        return false;
    }

    @Override
    public void setTransactionSuccessful() {

    }

    @Override
    public void execSQL(String sql, Object[] bindArgs) throws SQLException {

    }

    @Override
    public DatabaseStatement compileStatement(String sql) {
        return new NoOperationDatabaseStatement();
    }

    @Override
    public boolean isDbLockedByCurrentThread() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public Object getRawDatabase() {
        return null;
    }
}
