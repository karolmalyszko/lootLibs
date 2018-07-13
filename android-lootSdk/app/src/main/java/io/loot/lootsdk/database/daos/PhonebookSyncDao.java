package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.ContactEntity;
import io.loot.lootsdk.models.orm.PhonebookSyncEntity;

@Dao
public interface PhonebookSyncDao extends BaseSynchronousDao<PhonebookSyncEntity>{

    @Query("SELECT * FROM PhonebookSyncEntity")
    LiveData<List<PhonebookSyncEntity>> loadAll();

    @Query("SELECT * FROM PhonebookSyncEntity")
    List<PhonebookSyncEntity> loadAllSynchronously();

    @Query("DELETE FROM PhonebookSyncEntity")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<PhonebookSyncEntity> entity);
}
