package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.AccountEntity;
import io.loot.lootsdk.models.orm.ServerInfoEntity;

@Dao
public interface ServerInfoDao extends BaseSynchronousDao<ServerInfoEntity> {

    @Query("SELECT * FROM ServerInfo LIMIT 1")
    LiveData<ServerInfoEntity> load();

    @Query("DELETE FROM ServerInfo")
    void deleteAll();
}
