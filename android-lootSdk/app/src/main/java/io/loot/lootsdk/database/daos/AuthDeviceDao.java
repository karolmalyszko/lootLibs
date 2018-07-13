package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.AuthDeviceEntity;

@Dao
public interface AuthDeviceDao extends BaseSynchronousDao<AuthDeviceEntity> {

    @Query("SELECT * FROM AuthDevice")
    LiveData<List<AuthDeviceEntity>> loadAll();

    @Query("DELETE FROM AuthDevice")
    void deleteAll();

    @Query("DELETE FROM AuthDevice WHERE deviceId = :deviceID")
    void deleteById(String deviceID);
}
