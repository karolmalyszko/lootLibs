package io.loot.lootsdk;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import io.loot.lootsdk.database.daos.ServerInfoDao;
import io.loot.lootsdk.models.orm.ServerInfoEntity;
import io.loot.lootsdk.utils.AppExecutors;


public class ServerInfo implements Observer<ServerInfoEntity> {

    private String mCachedServerDate;
    private ServerInfoDao mServerInfoDao;

    ServerInfo(LootSDK lootSDK) {
        mServerInfoDao = lootSDK.getDatabase().serverInfoDao();
    }

    public void fetch() {
        mServerInfoDao.load().observeForever(this);
    }

    public String getServerDate() {
        return mCachedServerDate == null ? "" : mCachedServerDate;
    }

    public void setServerDate(String date) {
        if (date == null || date.isEmpty()) {
            return;
        }

        mCachedServerDate = date;
        final ServerInfoEntity serverInfoEntity = new ServerInfoEntity();
        serverInfoEntity.setServerDate(mCachedServerDate);

        AppExecutors.get().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mServerInfoDao.insert(serverInfoEntity);
            }
        });
    }

    public void clearCached() {
        AppExecutors.get().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                mServerInfoDao.load().removeObserver(ServerInfo.this);
            }
        });
        mCachedServerDate = null;
    }

    @Override
    public void onChanged(@Nullable ServerInfoEntity serverInfoEntity) {
        if (serverInfoEntity == null) {
            return;
        }

        mCachedServerDate = serverInfoEntity.getServerDate();
    }
}
