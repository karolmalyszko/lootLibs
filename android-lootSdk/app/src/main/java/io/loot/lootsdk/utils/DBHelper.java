package io.loot.lootsdk.utils;

import android.content.Context;
import android.database.Cursor;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.SecureRandom;

import io.loot.lootsdk.database.NoOperationDatabase;
import io.loot.lootsdk.exceptions.NotInitializedException;
import io.loot.lootsdk.models.orm.DaoMaster;
import io.loot.lootsdk.models.orm.DaoSession;
import io.loot.lootsdk.models.orm.SessionEntityDao;

public class DBHelper {
    public static final String DB_NAME = "LootSDK.db";
    private static DBHelper sInstance;
    private Database mDb = null;
    private DaoSession mSession = null;
    private Context mContext = null;
    private boolean mIsInitialized = false;
    private KeyStoreUtil mKeyStoreUtil;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DBHelper();
        }

        return sInstance;
    }

    public void init(Context context) {
        mContext = context;
        mIsInitialized = true;
        mKeyStoreUtil = KeyStoreUtil.getInstance(mContext);
    }

    private DaoMaster getMaster() {
        if (mDb == null || mDb instanceof NoOperationDatabase) {

            try {
                mDb = getDatabase(DB_NAME, false, false);
            } catch (Exception exception) {
                mDb = null;
            }
        }

        if (mDb == null) {
            mDb = new NoOperationDatabase();
        }

        return new DaoMaster(mDb);
    }

    public DaoSession getSession(boolean newSession) {
        if (newSession) {
            return getMaster().newSession();
        }

        if (mSession == null) {
            mSession = getMaster().newSession();
            checkDBOConsistency();
        }

        return mSession;
    }

    private synchronized Database getDatabase(String name, boolean readOnly, boolean isRetry) {
        if (!mIsInitialized) {
            throw new NotInitializedException();
        }

        try {
            readOnly = false;
            DaoMaster.DevOpenHelper helper = new MyOpenHelper(mContext, name, null);
            try {
                if (readOnly) {
                    return helper.getEncryptedReadableDb(getPassword());
                } else {
                    return helper.getEncryptedWritableDb(getPassword());
                }
            }
            catch (SQLException exception) {
                clearDBandPass(name);
                if (!isRetry) {
                    return getDatabase(name, readOnly, true);
                }
                throw exception;
            }
        } catch (Exception ex) {
            clearDBandPass(name);
            if (!isRetry) {
                return getDatabase(name, readOnly, true);
            }
            throw ex;
        } catch (Error err) {
            throw err;
        }
    }

    public void clearDBandPass(String name) {
        if (name == null || name.isEmpty()) {
            name = DB_NAME;
        }
        try {
            mContext.deleteDatabase(name);
            mKeyStoreUtil.clear();
            mKeyStoreUtil.saveDBPass("");
        } catch (Exception e) {

        } finally {
            mDb = null;
        }
    }

    public void deleteAllDBObjects() {
        if (mDb == null) {
            return;
        }
        try {
            DaoMaster.dropAllTables(mDb, true);
            DaoMaster.createAllTables(mDb, true);
        } catch (Exception e) {
        }
    }

    private void checkDBOConsistency() {
        for (AbstractDao abstractDao : mSession.getAllDaos()) {
            try{
                abstractDao.loadAll();
            }
            catch (SQLiteException ex) {
                if (abstractDao instanceof SessionEntityDao) {
                    deleteAllDBObjects();
                    clearDBandPass(DB_NAME);
                    mSession = getMaster().newSession();
                    return;
                }
                else {
                    dropTableIfExist(abstractDao);
                    createTableIfNotExist(abstractDao);
                }
            }
        }

    }

    private void createTableIfNotExist(Object o) {
        Method methodToFind = null;
        try {
            methodToFind = o.getClass().getMethod("createTable", Database.class, boolean.class);
            if(methodToFind != null) {
                methodToFind.invoke(o, mDb, false);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void dropTableIfExist(Object o) {
        Method methodToFind = null;
        try {
            methodToFind = o.getClass().getMethod("dropTable", Database.class, boolean.class);
            if(methodToFind != null) {
                methodToFind.invoke(o, mDb, false);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private String getPassword() {
        if (mKeyStoreUtil.getDBPass() == null || mKeyStoreUtil.getDBPass().isEmpty()) {
            mKeyStoreUtil.saveDBPass(generatePassword());
        }
        return mKeyStoreUtil.getDBPass();
    }

    private String generatePassword() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isTableExists(String tableName) {
        if (mDb == null || mDb instanceof NoOperationDatabase) {

            try {
                mDb = getDatabase(DB_NAME, false, false);
            } catch (Exception exception) {
                mDb = null;
            }
        }

        if (mDb == null) {
            return false;
        }

        Cursor cursor = mDb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                return true;
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return false;
    }

    private class MyOpenHelper extends DaoMaster.DevOpenHelper {
        public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name);
        }

    }
}
