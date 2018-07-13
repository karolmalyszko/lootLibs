package io.loot.lootsdk.database.utils;

import android.os.AsyncTask;

import io.loot.lootsdk.database.daos.BaseSynchronousDao;

@SuppressWarnings("unchecked")
public class DatabaseAccessExecutor<T> extends AsyncTask<T, Void, Void> {

    private enum Method {
        INSERT, DELETE, DELETE_ALL
    }

    public static <T> DatabaseAccessExecutor<T> with(BaseSynchronousDao<T> dao) {
        return new DatabaseAccessExecutor<>(dao);
    }

    private Method method;
    private BaseSynchronousDao<T> dao;

    private DatabaseAccessExecutor(BaseSynchronousDao<T> dao) {
        this.dao = dao;
    }

    public void insert(T... entities) {
        this.method = Method.INSERT;
        this.execute(entities);
    }

    public void delete(T entity) {
        this.method = Method.DELETE;
        this.execute(entity);
    }

    @Override
    protected Void doInBackground(T... params) {
        switch (method) {
            case INSERT:
                dao.insert(params);
                break;
            case DELETE:
                dao.delete(params[0]);
                break;
        }

        return null;
    }
}
