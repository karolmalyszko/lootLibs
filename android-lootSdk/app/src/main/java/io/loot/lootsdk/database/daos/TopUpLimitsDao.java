package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import io.loot.lootsdk.models.orm.TopUpLimitsEntity;

@Dao
public interface TopUpLimitsDao extends BaseSynchronousDao<TopUpLimitsEntity> {

    @Query("SELECT * FROM TopUpLimits LIMIT 1")
    LiveData<TopUpLimitsEntity> load();

    @Query("DELETE FROM TopUpLimits")
    void deleteAll();
}
