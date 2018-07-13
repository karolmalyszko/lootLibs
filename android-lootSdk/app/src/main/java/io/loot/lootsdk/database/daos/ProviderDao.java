package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.ProviderEntity;

@Dao
public interface ProviderDao extends BaseSynchronousDao<ProviderEntity> {

    @Query("SELECT * FROM Provider")
    LiveData<List<ProviderEntity>> loadAll();

    @Query("SELECT * FROM Provider WHERE id = :providerId")
    ProviderEntity loadByIdSynchronously(String providerId);

    @Query("DELETE FROM Provider")
    void deleteAll();
}
