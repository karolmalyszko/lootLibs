package io.loot.lootsdk.database;

import org.greenrobot.greendao.database.DatabaseStatement;


public class NoOperationDatabaseStatement implements DatabaseStatement {
    @Override
    public void execute() {

    }

    @Override
    public long simpleQueryForLong() {
        return 0;
    }

    @Override
    public void bindNull(int index) {

    }

    @Override
    public long executeInsert() {
        return 0;
    }

    @Override
    public void bindString(int index, String value) {

    }

    @Override
    public void bindBlob(int index, byte[] value) {

    }

    @Override
    public void bindLong(int index, long value) {

    }

    @Override
    public void clearBindings() {

    }

    @Override
    public void bindDouble(int index, double value) {

    }

    @Override
    public void close() {

    }

    @Override
    public Object getRawStatement() {
        return null;
    }
}
